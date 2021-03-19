package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.CustomerManager;
import com.wmes.appserver.repository.CustomerManagerRepository;
import com.wmes.appserver.service.CustomerManagerService;
import com.wmes.appserver.service.dto.CustomerManagerDTO;
import com.wmes.appserver.service.mapper.CustomerManagerMapper;
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
 * Integration tests for the {@link CustomerManagerResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class CustomerManagerResourceIT {

    private static final String DEFAULT_CUSTOMER_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_MANAGER_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MANAGER_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_MANAGER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MANAGER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_MANGER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MANGER_EMAIL = "BBBBBBBBBB";

    @Autowired
    private CustomerManagerRepository customerManagerRepository;

    @Autowired
    private CustomerManagerMapper customerManagerMapper;

    @Autowired
    private CustomerManagerService customerManagerService;

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

    private MockMvc restCustomerManagerMockMvc;

    private CustomerManager customerManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerManagerResource customerManagerResource = new CustomerManagerResource(customerManagerService);
        this.restCustomerManagerMockMvc = MockMvcBuilders.standaloneSetup(customerManagerResource)
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
    public static CustomerManager createEntity(EntityManager em) {
        CustomerManager customerManager = new CustomerManager()
            .customerManagerName(DEFAULT_CUSTOMER_MANAGER_NAME)
            .customerManagerDepartment(DEFAULT_CUSTOMER_MANAGER_DEPARTMENT)
            .customerManagerNumber(DEFAULT_CUSTOMER_MANAGER_NUMBER)
            .customerMangerEmail(DEFAULT_CUSTOMER_MANGER_EMAIL);
        return customerManager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerManager createUpdatedEntity(EntityManager em) {
        CustomerManager customerManager = new CustomerManager()
            .customerManagerName(UPDATED_CUSTOMER_MANAGER_NAME)
            .customerManagerDepartment(UPDATED_CUSTOMER_MANAGER_DEPARTMENT)
            .customerManagerNumber(UPDATED_CUSTOMER_MANAGER_NUMBER)
            .customerMangerEmail(UPDATED_CUSTOMER_MANGER_EMAIL);
        return customerManager;
    }

    @BeforeEach
    public void initTest() {
        customerManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerManager() throws Exception {
        int databaseSizeBeforeCreate = customerManagerRepository.findAll().size();

        // Create the CustomerManager
        CustomerManagerDTO customerManagerDTO = customerManagerMapper.toDto(customerManager);
        restCustomerManagerMockMvc.perform(post("/api/customer-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerManagerDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerManagerName()).isEqualTo(DEFAULT_CUSTOMER_MANAGER_NAME);
        assertThat(testCustomerManager.getCustomerManagerDepartment()).isEqualTo(DEFAULT_CUSTOMER_MANAGER_DEPARTMENT);
        assertThat(testCustomerManager.getCustomerManagerNumber()).isEqualTo(DEFAULT_CUSTOMER_MANAGER_NUMBER);
        assertThat(testCustomerManager.getCustomerMangerEmail()).isEqualTo(DEFAULT_CUSTOMER_MANGER_EMAIL);
    }

    @Test
    @Transactional
    public void createCustomerManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerManagerRepository.findAll().size();

        // Create the CustomerManager with an existing ID
        customerManager.setId(1L);
        CustomerManagerDTO customerManagerDTO = customerManagerMapper.toDto(customerManager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerManagerMockMvc.perform(post("/api/customer-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerManagers() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        // Get all the customerManagerList
        restCustomerManagerMockMvc.perform(get("/api/customer-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerManagerName").value(hasItem(DEFAULT_CUSTOMER_MANAGER_NAME.toString())))
            .andExpect(jsonPath("$.[*].customerManagerDepartment").value(hasItem(DEFAULT_CUSTOMER_MANAGER_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].customerManagerNumber").value(hasItem(DEFAULT_CUSTOMER_MANAGER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].customerMangerEmail").value(hasItem(DEFAULT_CUSTOMER_MANGER_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        // Get the customerManager
        restCustomerManagerMockMvc.perform(get("/api/customer-managers/{id}", customerManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerManager.getId().intValue()))
            .andExpect(jsonPath("$.customerManagerName").value(DEFAULT_CUSTOMER_MANAGER_NAME.toString()))
            .andExpect(jsonPath("$.customerManagerDepartment").value(DEFAULT_CUSTOMER_MANAGER_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.customerManagerNumber").value(DEFAULT_CUSTOMER_MANAGER_NUMBER.toString()))
            .andExpect(jsonPath("$.customerMangerEmail").value(DEFAULT_CUSTOMER_MANGER_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerManager() throws Exception {
        // Get the customerManager
        restCustomerManagerMockMvc.perform(get("/api/customer-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();

        // Update the customerManager
        CustomerManager updatedCustomerManager = customerManagerRepository.findById(customerManager.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerManager are not directly saved in db
        em.detach(updatedCustomerManager);
        updatedCustomerManager
            .customerManagerName(UPDATED_CUSTOMER_MANAGER_NAME)
            .customerManagerDepartment(UPDATED_CUSTOMER_MANAGER_DEPARTMENT)
            .customerManagerNumber(UPDATED_CUSTOMER_MANAGER_NUMBER)
            .customerMangerEmail(UPDATED_CUSTOMER_MANGER_EMAIL);
        CustomerManagerDTO customerManagerDTO = customerManagerMapper.toDto(updatedCustomerManager);

        restCustomerManagerMockMvc.perform(put("/api/customer-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerManagerDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerManagerName()).isEqualTo(UPDATED_CUSTOMER_MANAGER_NAME);
        assertThat(testCustomerManager.getCustomerManagerDepartment()).isEqualTo(UPDATED_CUSTOMER_MANAGER_DEPARTMENT);
        assertThat(testCustomerManager.getCustomerManagerNumber()).isEqualTo(UPDATED_CUSTOMER_MANAGER_NUMBER);
        assertThat(testCustomerManager.getCustomerMangerEmail()).isEqualTo(UPDATED_CUSTOMER_MANGER_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();

        // Create the CustomerManager
        CustomerManagerDTO customerManagerDTO = customerManagerMapper.toDto(customerManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc.perform(put("/api/customer-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeDelete = customerManagerRepository.findAll().size();

        // Delete the customerManager
        restCustomerManagerMockMvc.perform(delete("/api/customer-managers/{id}", customerManager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerManager.class);
        CustomerManager customerManager1 = new CustomerManager();
        customerManager1.setId(1L);
        CustomerManager customerManager2 = new CustomerManager();
        customerManager2.setId(customerManager1.getId());
        assertThat(customerManager1).isEqualTo(customerManager2);
        customerManager2.setId(2L);
        assertThat(customerManager1).isNotEqualTo(customerManager2);
        customerManager1.setId(null);
        assertThat(customerManager1).isNotEqualTo(customerManager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerManagerDTO.class);
        CustomerManagerDTO customerManagerDTO1 = new CustomerManagerDTO();
        customerManagerDTO1.setId(1L);
        CustomerManagerDTO customerManagerDTO2 = new CustomerManagerDTO();
        assertThat(customerManagerDTO1).isNotEqualTo(customerManagerDTO2);
        customerManagerDTO2.setId(customerManagerDTO1.getId());
        assertThat(customerManagerDTO1).isEqualTo(customerManagerDTO2);
        customerManagerDTO2.setId(2L);
        assertThat(customerManagerDTO1).isNotEqualTo(customerManagerDTO2);
        customerManagerDTO1.setId(null);
        assertThat(customerManagerDTO1).isNotEqualTo(customerManagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerManagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerManagerMapper.fromId(null)).isNull();
    }
}
