package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.Material;
import com.wmes.appserver.repository.MaterialRepository;
import com.wmes.appserver.service.MaterialService;
import com.wmes.appserver.service.dto.MaterialDTO;
import com.wmes.appserver.service.mapper.MaterialMapper;
import com.wmes.appserver.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wmes.appserver.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class MaterialResourceIT {

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMaterialMockMvc;

    private Material material;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialResource materialResource = new MaterialResource(materialService);
        this.restMaterialMockMvc = MockMvcBuilders.standaloneSetup(materialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material()
            .materialName(DEFAULT_MATERIAL_NAME);
        return material;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material()
            .materialName(UPDATED_MATERIAL_NAME);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);
        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void createMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // Create the Material with an existing ID
        material.setId(1L);
        MaterialDTO materialDTO = materialMapper.toDto(material);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc.perform(get("/api/materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId().intValue()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).get();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial
            .materialName(UPDATED_MATERIAL_NAME);
        MaterialDTO materialDTO = materialMapper.toDto(updatedMaterial);

        restMaterialMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc.perform(delete("/api/materials/{id}", material.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Material.class);
        Material material1 = new Material();
        material1.setId(1L);
        Material material2 = new Material();
        material2.setId(material1.getId());
        assertThat(material1).isEqualTo(material2);
        material2.setId(2L);
        assertThat(material1).isNotEqualTo(material2);
        material1.setId(null);
        assertThat(material1).isNotEqualTo(material2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialDTO.class);
        MaterialDTO materialDTO1 = new MaterialDTO();
        materialDTO1.setId(1L);
        MaterialDTO materialDTO2 = new MaterialDTO();
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
        materialDTO2.setId(materialDTO1.getId());
        assertThat(materialDTO1).isEqualTo(materialDTO2);
        materialDTO2.setId(2L);
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
        materialDTO1.setId(null);
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialMapper.fromId(null)).isNull();
    }
}
