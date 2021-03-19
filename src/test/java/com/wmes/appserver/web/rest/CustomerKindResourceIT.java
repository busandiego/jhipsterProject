package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.CustomerKind;
import com.wmes.appserver.repository.CustomerKindRepository;
import com.wmes.appserver.service.CustomerKindService;
import com.wmes.appserver.service.dto.CustomerKindDTO;
import com.wmes.appserver.service.mapper.CustomerKindMapper;
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
 * Integration tests for the {@link CustomerKindResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class CustomerKindResourceIT {

    private static final String DEFAULT_CUSTOMER_KIND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_KIND_NAME = "BBBBBBBBBB";

    @Autowired
    private CustomerKindRepository customerKindRepository;

    @Autowired
    private CustomerKindMapper customerKindMapper;

    @Autowired
    private CustomerKindService customerKindService;

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

    private MockMvc restCustomerKindMockMvc;

    private CustomerKind customerKind;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerKindResource customerKindResource = new CustomerKindResource(customerKindService);
        this.restCustomerKindMockMvc = MockMvcBuilders.standaloneSetup(customerKindResource)
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
    public static CustomerKind createEntity(EntityManager em) {
        CustomerKind customerKind = new CustomerKind()
            .customerKindName(DEFAULT_CUSTOMER_KIND_NAME);
        return customerKind;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerKind createUpdatedEntity(EntityManager em) {
        CustomerKind customerKind = new CustomerKind()
            .customerKindName(UPDATED_CUSTOMER_KIND_NAME);
        return customerKind;
    }

    @BeforeEach
    public void initTest() {
        customerKind = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerKind() throws Exception {
        int databaseSizeBeforeCreate = customerKindRepository.findAll().size();

        // Create the CustomerKind
        CustomerKindDTO customerKindDTO = customerKindMapper.toDto(customerKind);
        restCustomerKindMockMvc.perform(post("/api/customer-kinds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerKindDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerKind in the database
        List<CustomerKind> customerKindList = customerKindRepository.findAll();
        assertThat(customerKindList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerKind testCustomerKind = customerKindList.get(customerKindList.size() - 1);
        assertThat(testCustomerKind.getCustomerKindName()).isEqualTo(DEFAULT_CUSTOMER_KIND_NAME);
    }

    @Test
    @Transactional
    public void createCustomerKindWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerKindRepository.findAll().size();

        // Create the CustomerKind with an existing ID
        customerKind.setId(1L);
        CustomerKindDTO customerKindDTO = customerKindMapper.toDto(customerKind);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerKindMockMvc.perform(post("/api/customer-kinds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerKindDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerKind in the database
        List<CustomerKind> customerKindList = customerKindRepository.findAll();
        assertThat(customerKindList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerKinds() throws Exception {
        // Initialize the database
        customerKindRepository.saveAndFlush(customerKind);

        // Get all the customerKindList
        restCustomerKindMockMvc.perform(get("/api/customer-kinds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerKind.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerKindName").value(hasItem(DEFAULT_CUSTOMER_KIND_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerKind() throws Exception {
        // Initialize the database
        customerKindRepository.saveAndFlush(customerKind);

        // Get the customerKind
        restCustomerKindMockMvc.perform(get("/api/customer-kinds/{id}", customerKind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerKind.getId().intValue()))
            .andExpect(jsonPath("$.customerKindName").value(DEFAULT_CUSTOMER_KIND_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerKind() throws Exception {
        // Get the customerKind
        restCustomerKindMockMvc.perform(get("/api/customer-kinds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerKind() throws Exception {
        // Initialize the database
        customerKindRepository.saveAndFlush(customerKind);

        int databaseSizeBeforeUpdate = customerKindRepository.findAll().size();

        // Update the customerKind
        CustomerKind updatedCustomerKind = customerKindRepository.findById(customerKind.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerKind are not directly saved in db
        em.detach(updatedCustomerKind);
        updatedCustomerKind
            .customerKindName(UPDATED_CUSTOMER_KIND_NAME);
        CustomerKindDTO customerKindDTO = customerKindMapper.toDto(updatedCustomerKind);

        restCustomerKindMockMvc.perform(put("/api/customer-kinds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerKindDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerKind in the database
        List<CustomerKind> customerKindList = customerKindRepository.findAll();
        assertThat(customerKindList).hasSize(databaseSizeBeforeUpdate);
        CustomerKind testCustomerKind = customerKindList.get(customerKindList.size() - 1);
        assertThat(testCustomerKind.getCustomerKindName()).isEqualTo(UPDATED_CUSTOMER_KIND_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerKind() throws Exception {
        int databaseSizeBeforeUpdate = customerKindRepository.findAll().size();

        // Create the CustomerKind
        CustomerKindDTO customerKindDTO = customerKindMapper.toDto(customerKind);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerKindMockMvc.perform(put("/api/customer-kinds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerKindDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerKind in the database
        List<CustomerKind> customerKindList = customerKindRepository.findAll();
        assertThat(customerKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerKind() throws Exception {
        // Initialize the database
        customerKindRepository.saveAndFlush(customerKind);

        int databaseSizeBeforeDelete = customerKindRepository.findAll().size();

        // Delete the customerKind
        restCustomerKindMockMvc.perform(delete("/api/customer-kinds/{id}", customerKind.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerKind> customerKindList = customerKindRepository.findAll();
        assertThat(customerKindList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerKind.class);
        CustomerKind customerKind1 = new CustomerKind();
        customerKind1.setId(1L);
        CustomerKind customerKind2 = new CustomerKind();
        customerKind2.setId(customerKind1.getId());
        assertThat(customerKind1).isEqualTo(customerKind2);
        customerKind2.setId(2L);
        assertThat(customerKind1).isNotEqualTo(customerKind2);
        customerKind1.setId(null);
        assertThat(customerKind1).isNotEqualTo(customerKind2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerKindDTO.class);
        CustomerKindDTO customerKindDTO1 = new CustomerKindDTO();
        customerKindDTO1.setId(1L);
        CustomerKindDTO customerKindDTO2 = new CustomerKindDTO();
        assertThat(customerKindDTO1).isNotEqualTo(customerKindDTO2);
        customerKindDTO2.setId(customerKindDTO1.getId());
        assertThat(customerKindDTO1).isEqualTo(customerKindDTO2);
        customerKindDTO2.setId(2L);
        assertThat(customerKindDTO1).isNotEqualTo(customerKindDTO2);
        customerKindDTO1.setId(null);
        assertThat(customerKindDTO1).isNotEqualTo(customerKindDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerKindMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerKindMapper.fromId(null)).isNull();
    }
}
