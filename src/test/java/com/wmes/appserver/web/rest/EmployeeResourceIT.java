package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.Employee;
import com.wmes.appserver.repository.EmployeeRepository;
import com.wmes.appserver.service.EmployeeService;
import com.wmes.appserver.service.dto.EmployeeDTO;
import com.wmes.appserver.service.mapper.EmployeeMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.wmes.appserver.web.rest.TestUtil.sameInstant;
import static com.wmes.appserver.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class EmployeeResourceIT {

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_SECURITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_SECURITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_EMERGENCY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_EMERGENCY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ZIP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ZIP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_DETAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_DETAIL_ADDRESS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EMPLOYEE_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EMPLOYEE_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EMPLOYEE_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_EMPLOYEE_LEAVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EMPLOYEE_LEAVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EMPLOYEE_LEAVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

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

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource employeeResource = new EmployeeResource(employeeService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .employeeSecurityNumber(DEFAULT_EMPLOYEE_SECURITY_NUMBER)
            .employeeEmergencyNumber(DEFAULT_EMPLOYEE_EMERGENCY_NUMBER)
            .employeeNumber(DEFAULT_EMPLOYEE_NUMBER)
            .employeeZipAddress(DEFAULT_EMPLOYEE_ZIP_ADDRESS)
            .employeeAddress(DEFAULT_EMPLOYEE_ADDRESS)
            .employeeDetailAddress(DEFAULT_EMPLOYEE_DETAIL_ADDRESS)
            .employeeStartDate(DEFAULT_EMPLOYEE_START_DATE)
            .employeeLeaveDate(DEFAULT_EMPLOYEE_LEAVE_DATE);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeSecurityNumber(UPDATED_EMPLOYEE_SECURITY_NUMBER)
            .employeeEmergencyNumber(UPDATED_EMPLOYEE_EMERGENCY_NUMBER)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .employeeZipAddress(UPDATED_EMPLOYEE_ZIP_ADDRESS)
            .employeeAddress(UPDATED_EMPLOYEE_ADDRESS)
            .employeeDetailAddress(UPDATED_EMPLOYEE_DETAIL_ADDRESS)
            .employeeStartDate(UPDATED_EMPLOYEE_START_DATE)
            .employeeLeaveDate(UPDATED_EMPLOYEE_LEAVE_DATE);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testEmployee.getEmployeeSecurityNumber()).isEqualTo(DEFAULT_EMPLOYEE_SECURITY_NUMBER);
        assertThat(testEmployee.getEmployeeEmergencyNumber()).isEqualTo(DEFAULT_EMPLOYEE_EMERGENCY_NUMBER);
        assertThat(testEmployee.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testEmployee.getEmployeeZipAddress()).isEqualTo(DEFAULT_EMPLOYEE_ZIP_ADDRESS);
        assertThat(testEmployee.getEmployeeAddress()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS);
        assertThat(testEmployee.getEmployeeDetailAddress()).isEqualTo(DEFAULT_EMPLOYEE_DETAIL_ADDRESS);
        assertThat(testEmployee.getEmployeeStartDate()).isEqualTo(DEFAULT_EMPLOYEE_START_DATE);
        assertThat(testEmployee.getEmployeeLeaveDate()).isEqualTo(DEFAULT_EMPLOYEE_LEAVE_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].employeeSecurityNumber").value(hasItem(DEFAULT_EMPLOYEE_SECURITY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].employeeEmergencyNumber").value(hasItem(DEFAULT_EMPLOYEE_EMERGENCY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].employeeZipAddress").value(hasItem(DEFAULT_EMPLOYEE_ZIP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].employeeAddress").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].employeeDetailAddress").value(hasItem(DEFAULT_EMPLOYEE_DETAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].employeeStartDate").value(hasItem(sameInstant(DEFAULT_EMPLOYEE_START_DATE))))
            .andExpect(jsonPath("$.[*].employeeLeaveDate").value(hasItem(sameInstant(DEFAULT_EMPLOYEE_LEAVE_DATE))));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.employeeSecurityNumber").value(DEFAULT_EMPLOYEE_SECURITY_NUMBER.toString()))
            .andExpect(jsonPath("$.employeeEmergencyNumber").value(DEFAULT_EMPLOYEE_EMERGENCY_NUMBER.toString()))
            .andExpect(jsonPath("$.employeeNumber").value(DEFAULT_EMPLOYEE_NUMBER.toString()))
            .andExpect(jsonPath("$.employeeZipAddress").value(DEFAULT_EMPLOYEE_ZIP_ADDRESS.toString()))
            .andExpect(jsonPath("$.employeeAddress").value(DEFAULT_EMPLOYEE_ADDRESS.toString()))
            .andExpect(jsonPath("$.employeeDetailAddress").value(DEFAULT_EMPLOYEE_DETAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.employeeStartDate").value(sameInstant(DEFAULT_EMPLOYEE_START_DATE)))
            .andExpect(jsonPath("$.employeeLeaveDate").value(sameInstant(DEFAULT_EMPLOYEE_LEAVE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeSecurityNumber(UPDATED_EMPLOYEE_SECURITY_NUMBER)
            .employeeEmergencyNumber(UPDATED_EMPLOYEE_EMERGENCY_NUMBER)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .employeeZipAddress(UPDATED_EMPLOYEE_ZIP_ADDRESS)
            .employeeAddress(UPDATED_EMPLOYEE_ADDRESS)
            .employeeDetailAddress(UPDATED_EMPLOYEE_DETAIL_ADDRESS)
            .employeeStartDate(UPDATED_EMPLOYEE_START_DATE)
            .employeeLeaveDate(UPDATED_EMPLOYEE_LEAVE_DATE);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testEmployee.getEmployeeSecurityNumber()).isEqualTo(UPDATED_EMPLOYEE_SECURITY_NUMBER);
        assertThat(testEmployee.getEmployeeEmergencyNumber()).isEqualTo(UPDATED_EMPLOYEE_EMERGENCY_NUMBER);
        assertThat(testEmployee.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testEmployee.getEmployeeZipAddress()).isEqualTo(UPDATED_EMPLOYEE_ZIP_ADDRESS);
        assertThat(testEmployee.getEmployeeAddress()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS);
        assertThat(testEmployee.getEmployeeDetailAddress()).isEqualTo(UPDATED_EMPLOYEE_DETAIL_ADDRESS);
        assertThat(testEmployee.getEmployeeStartDate()).isEqualTo(UPDATED_EMPLOYEE_START_DATE);
        assertThat(testEmployee.getEmployeeLeaveDate()).isEqualTo(UPDATED_EMPLOYEE_LEAVE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setId(1L);
        Employee employee2 = new Employee();
        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);
        employee2.setId(2L);
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setId(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDTO.class);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setId(1L);
        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO2.setId(employeeDTO1.getId());
        assertThat(employeeDTO1).isEqualTo(employeeDTO2);
        employeeDTO2.setId(2L);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO1.setId(null);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeMapper.fromId(null)).isNull();
    }
}
