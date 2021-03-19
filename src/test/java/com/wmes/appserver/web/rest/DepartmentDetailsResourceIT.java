package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.DepartmentDetails;
import com.wmes.appserver.repository.DepartmentDetailsRepository;
import com.wmes.appserver.service.DepartmentDetailsService;
import com.wmes.appserver.service.dto.DepartmentDetailsDTO;
import com.wmes.appserver.service.mapper.DepartmentDetailsMapper;
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
 * Integration tests for the {@link DepartmentDetailsResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class DepartmentDetailsResourceIT {

    private static final String DEFAULT_DEPARTMENT_DETAILS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_DETAILS_NAME = "BBBBBBBBBB";

    @Autowired
    private DepartmentDetailsRepository departmentDetailsRepository;

    @Autowired
    private DepartmentDetailsMapper departmentDetailsMapper;

    @Autowired
    private DepartmentDetailsService departmentDetailsService;

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

    private MockMvc restDepartmentDetailsMockMvc;

    private DepartmentDetails departmentDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentDetailsResource departmentDetailsResource = new DepartmentDetailsResource(departmentDetailsService);
        this.restDepartmentDetailsMockMvc = MockMvcBuilders.standaloneSetup(departmentDetailsResource)
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
    public static DepartmentDetails createEntity(EntityManager em) {
        DepartmentDetails departmentDetails = new DepartmentDetails()
            .departmentDetailsName(DEFAULT_DEPARTMENT_DETAILS_NAME);
        return departmentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentDetails createUpdatedEntity(EntityManager em) {
        DepartmentDetails departmentDetails = new DepartmentDetails()
            .departmentDetailsName(UPDATED_DEPARTMENT_DETAILS_NAME);
        return departmentDetails;
    }

    @BeforeEach
    public void initTest() {
        departmentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartmentDetails() throws Exception {
        int databaseSizeBeforeCreate = departmentDetailsRepository.findAll().size();

        // Create the DepartmentDetails
        DepartmentDetailsDTO departmentDetailsDTO = departmentDetailsMapper.toDto(departmentDetails);
        restDepartmentDetailsMockMvc.perform(post("/api/department-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the DepartmentDetails in the database
        List<DepartmentDetails> departmentDetailsList = departmentDetailsRepository.findAll();
        assertThat(departmentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentDetails testDepartmentDetails = departmentDetailsList.get(departmentDetailsList.size() - 1);
        assertThat(testDepartmentDetails.getDepartmentDetailsName()).isEqualTo(DEFAULT_DEPARTMENT_DETAILS_NAME);
    }

    @Test
    @Transactional
    public void createDepartmentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentDetailsRepository.findAll().size();

        // Create the DepartmentDetails with an existing ID
        departmentDetails.setId(1L);
        DepartmentDetailsDTO departmentDetailsDTO = departmentDetailsMapper.toDto(departmentDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentDetailsMockMvc.perform(post("/api/department-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentDetails in the database
        List<DepartmentDetails> departmentDetailsList = departmentDetailsRepository.findAll();
        assertThat(departmentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepartmentDetails() throws Exception {
        // Initialize the database
        departmentDetailsRepository.saveAndFlush(departmentDetails);

        // Get all the departmentDetailsList
        restDepartmentDetailsMockMvc.perform(get("/api/department-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].departmentDetailsName").value(hasItem(DEFAULT_DEPARTMENT_DETAILS_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDepartmentDetails() throws Exception {
        // Initialize the database
        departmentDetailsRepository.saveAndFlush(departmentDetails);

        // Get the departmentDetails
        restDepartmentDetailsMockMvc.perform(get("/api/department-details/{id}", departmentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departmentDetails.getId().intValue()))
            .andExpect(jsonPath("$.departmentDetailsName").value(DEFAULT_DEPARTMENT_DETAILS_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartmentDetails() throws Exception {
        // Get the departmentDetails
        restDepartmentDetailsMockMvc.perform(get("/api/department-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentDetails() throws Exception {
        // Initialize the database
        departmentDetailsRepository.saveAndFlush(departmentDetails);

        int databaseSizeBeforeUpdate = departmentDetailsRepository.findAll().size();

        // Update the departmentDetails
        DepartmentDetails updatedDepartmentDetails = departmentDetailsRepository.findById(departmentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDepartmentDetails are not directly saved in db
        em.detach(updatedDepartmentDetails);
        updatedDepartmentDetails
            .departmentDetailsName(UPDATED_DEPARTMENT_DETAILS_NAME);
        DepartmentDetailsDTO departmentDetailsDTO = departmentDetailsMapper.toDto(updatedDepartmentDetails);

        restDepartmentDetailsMockMvc.perform(put("/api/department-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the DepartmentDetails in the database
        List<DepartmentDetails> departmentDetailsList = departmentDetailsRepository.findAll();
        assertThat(departmentDetailsList).hasSize(databaseSizeBeforeUpdate);
        DepartmentDetails testDepartmentDetails = departmentDetailsList.get(departmentDetailsList.size() - 1);
        assertThat(testDepartmentDetails.getDepartmentDetailsName()).isEqualTo(UPDATED_DEPARTMENT_DETAILS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartmentDetails() throws Exception {
        int databaseSizeBeforeUpdate = departmentDetailsRepository.findAll().size();

        // Create the DepartmentDetails
        DepartmentDetailsDTO departmentDetailsDTO = departmentDetailsMapper.toDto(departmentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentDetailsMockMvc.perform(put("/api/department-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentDetails in the database
        List<DepartmentDetails> departmentDetailsList = departmentDetailsRepository.findAll();
        assertThat(departmentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartmentDetails() throws Exception {
        // Initialize the database
        departmentDetailsRepository.saveAndFlush(departmentDetails);

        int databaseSizeBeforeDelete = departmentDetailsRepository.findAll().size();

        // Delete the departmentDetails
        restDepartmentDetailsMockMvc.perform(delete("/api/department-details/{id}", departmentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartmentDetails> departmentDetailsList = departmentDetailsRepository.findAll();
        assertThat(departmentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentDetails.class);
        DepartmentDetails departmentDetails1 = new DepartmentDetails();
        departmentDetails1.setId(1L);
        DepartmentDetails departmentDetails2 = new DepartmentDetails();
        departmentDetails2.setId(departmentDetails1.getId());
        assertThat(departmentDetails1).isEqualTo(departmentDetails2);
        departmentDetails2.setId(2L);
        assertThat(departmentDetails1).isNotEqualTo(departmentDetails2);
        departmentDetails1.setId(null);
        assertThat(departmentDetails1).isNotEqualTo(departmentDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentDetailsDTO.class);
        DepartmentDetailsDTO departmentDetailsDTO1 = new DepartmentDetailsDTO();
        departmentDetailsDTO1.setId(1L);
        DepartmentDetailsDTO departmentDetailsDTO2 = new DepartmentDetailsDTO();
        assertThat(departmentDetailsDTO1).isNotEqualTo(departmentDetailsDTO2);
        departmentDetailsDTO2.setId(departmentDetailsDTO1.getId());
        assertThat(departmentDetailsDTO1).isEqualTo(departmentDetailsDTO2);
        departmentDetailsDTO2.setId(2L);
        assertThat(departmentDetailsDTO1).isNotEqualTo(departmentDetailsDTO2);
        departmentDetailsDTO1.setId(null);
        assertThat(departmentDetailsDTO1).isNotEqualTo(departmentDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departmentDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departmentDetailsMapper.fromId(null)).isNull();
    }
}
