package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.Customer;
import com.wmes.appserver.repository.CustomerRepository;
import com.wmes.appserver.service.CustomerService;
import com.wmes.appserver.service.dto.CustomerDTO;
import com.wmes.appserver.service.mapper.CustomerMapper;
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
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class CustomerResourceIT {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_REPRESENTATIVE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REPRESENTATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_REGISTRATION_NUM = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REGISTRATION_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_REPRESENTATIVE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REPRESENTATIVE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_EMAIL = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

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

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerRepresentative(DEFAULT_CUSTOMER_REPRESENTATIVE)
            .customerRegistrationNum(DEFAULT_CUSTOMER_REGISTRATION_NUM)
            .customerRepresentativeNum(DEFAULT_CUSTOMER_REPRESENTATIVE_NUM)
            .customerType(DEFAULT_CUSTOMER_TYPE)
            .customerCategory(DEFAULT_CUSTOMER_CATEGORY)
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .customerEmail(DEFAULT_CUSTOMER_EMAIL);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerRepresentative(UPDATED_CUSTOMER_REPRESENTATIVE)
            .customerRegistrationNum(UPDATED_CUSTOMER_REGISTRATION_NUM)
            .customerRepresentativeNum(UPDATED_CUSTOMER_REPRESENTATIVE_NUM)
            .customerType(UPDATED_CUSTOMER_TYPE)
            .customerCategory(UPDATED_CUSTOMER_CATEGORY)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerEmail(UPDATED_CUSTOMER_EMAIL);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testCustomer.getCustomerRepresentative()).isEqualTo(DEFAULT_CUSTOMER_REPRESENTATIVE);
        assertThat(testCustomer.getCustomerRegistrationNum()).isEqualTo(DEFAULT_CUSTOMER_REGISTRATION_NUM);
        assertThat(testCustomer.getCustomerRepresentativeNum()).isEqualTo(DEFAULT_CUSTOMER_REPRESENTATIVE_NUM);
        assertThat(testCustomer.getCustomerType()).isEqualTo(DEFAULT_CUSTOMER_TYPE);
        assertThat(testCustomer.getCustomerCategory()).isEqualTo(DEFAULT_CUSTOMER_CATEGORY);
        assertThat(testCustomer.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(DEFAULT_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCustomerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustomerName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerRepresentativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustomerRepresentative(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].customerRepresentative").value(hasItem(DEFAULT_CUSTOMER_REPRESENTATIVE.toString())))
            .andExpect(jsonPath("$.[*].customerRegistrationNum").value(hasItem(DEFAULT_CUSTOMER_REGISTRATION_NUM.toString())))
            .andExpect(jsonPath("$.[*].customerRepresentativeNum").value(hasItem(DEFAULT_CUSTOMER_REPRESENTATIVE_NUM.toString())))
            .andExpect(jsonPath("$.[*].customerType").value(hasItem(DEFAULT_CUSTOMER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customerCategory").value(hasItem(DEFAULT_CUSTOMER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].customerEmail").value(hasItem(DEFAULT_CUSTOMER_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.customerRepresentative").value(DEFAULT_CUSTOMER_REPRESENTATIVE.toString()))
            .andExpect(jsonPath("$.customerRegistrationNum").value(DEFAULT_CUSTOMER_REGISTRATION_NUM.toString()))
            .andExpect(jsonPath("$.customerRepresentativeNum").value(DEFAULT_CUSTOMER_REPRESENTATIVE_NUM.toString()))
            .andExpect(jsonPath("$.customerType").value(DEFAULT_CUSTOMER_TYPE.toString()))
            .andExpect(jsonPath("$.customerCategory").value(DEFAULT_CUSTOMER_CATEGORY.toString()))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER.toString()))
            .andExpect(jsonPath("$.customerEmail").value(DEFAULT_CUSTOMER_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerRepresentative(UPDATED_CUSTOMER_REPRESENTATIVE)
            .customerRegistrationNum(UPDATED_CUSTOMER_REGISTRATION_NUM)
            .customerRepresentativeNum(UPDATED_CUSTOMER_REPRESENTATIVE_NUM)
            .customerType(UPDATED_CUSTOMER_TYPE)
            .customerCategory(UPDATED_CUSTOMER_CATEGORY)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerEmail(UPDATED_CUSTOMER_EMAIL);
        CustomerDTO customerDTO = customerMapper.toDto(updatedCustomer);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomer.getCustomerRepresentative()).isEqualTo(UPDATED_CUSTOMER_REPRESENTATIVE);
        assertThat(testCustomer.getCustomerRegistrationNum()).isEqualTo(UPDATED_CUSTOMER_REGISTRATION_NUM);
        assertThat(testCustomer.getCustomerRepresentativeNum()).isEqualTo(UPDATED_CUSTOMER_REPRESENTATIVE_NUM);
        assertThat(testCustomer.getCustomerType()).isEqualTo(UPDATED_CUSTOMER_TYPE);
        assertThat(testCustomer.getCustomerCategory()).isEqualTo(UPDATED_CUSTOMER_CATEGORY);
        assertThat(testCustomer.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerDTO.class);
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        CustomerDTO customerDTO2 = new CustomerDTO();
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO2.setId(customerDTO1.getId());
        assertThat(customerDTO1).isEqualTo(customerDTO2);
        customerDTO2.setId(2L);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO1.setId(null);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerMapper.fromId(null)).isNull();
    }
}
