package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_security_number")
    private String employeeSecurityNumber;

    @Column(name = "employee_emergency_number")
    private String employeeEmergencyNumber;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "employee_zip_address")
    private String employeeZipAddress;

    @Column(name = "employee_address")
    private String employeeAddress;

    @Column(name = "employee_detail_address")
    private String employeeDetailAddress;

    @Column(name = "employee_start_date")
    private ZonedDateTime employeeStartDate;

    @Column(name = "employee_leave_date")
    private ZonedDateTime employeeLeaveDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Department department;

    @OneToOne
    @JoinColumn(unique = true)
    private DepartmentDetails departmentDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private Position position;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Business business;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Employee employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSecurityNumber() {
        return employeeSecurityNumber;
    }

    public Employee employeeSecurityNumber(String employeeSecurityNumber) {
        this.employeeSecurityNumber = employeeSecurityNumber;
        return this;
    }

    public void setEmployeeSecurityNumber(String employeeSecurityNumber) {
        this.employeeSecurityNumber = employeeSecurityNumber;
    }

    public String getEmployeeEmergencyNumber() {
        return employeeEmergencyNumber;
    }

    public Employee employeeEmergencyNumber(String employeeEmergencyNumber) {
        this.employeeEmergencyNumber = employeeEmergencyNumber;
        return this;
    }

    public void setEmployeeEmergencyNumber(String employeeEmergencyNumber) {
        this.employeeEmergencyNumber = employeeEmergencyNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Employee employeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeZipAddress() {
        return employeeZipAddress;
    }

    public Employee employeeZipAddress(String employeeZipAddress) {
        this.employeeZipAddress = employeeZipAddress;
        return this;
    }

    public void setEmployeeZipAddress(String employeeZipAddress) {
        this.employeeZipAddress = employeeZipAddress;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public Employee employeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
        return this;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeDetailAddress() {
        return employeeDetailAddress;
    }

    public Employee employeeDetailAddress(String employeeDetailAddress) {
        this.employeeDetailAddress = employeeDetailAddress;
        return this;
    }

    public void setEmployeeDetailAddress(String employeeDetailAddress) {
        this.employeeDetailAddress = employeeDetailAddress;
    }

    public ZonedDateTime getEmployeeStartDate() {
        return employeeStartDate;
    }

    public Employee employeeStartDate(ZonedDateTime employeeStartDate) {
        this.employeeStartDate = employeeStartDate;
        return this;
    }

    public void setEmployeeStartDate(ZonedDateTime employeeStartDate) {
        this.employeeStartDate = employeeStartDate;
    }

    public ZonedDateTime getEmployeeLeaveDate() {
        return employeeLeaveDate;
    }

    public Employee employeeLeaveDate(ZonedDateTime employeeLeaveDate) {
        this.employeeLeaveDate = employeeLeaveDate;
        return this;
    }

    public void setEmployeeLeaveDate(ZonedDateTime employeeLeaveDate) {
        this.employeeLeaveDate = employeeLeaveDate;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DepartmentDetails getDepartmentDetails() {
        return departmentDetails;
    }

    public Employee departmentDetails(DepartmentDetails departmentDetails) {
        this.departmentDetails = departmentDetails;
        return this;
    }

    public void setDepartmentDetails(DepartmentDetails departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public Position getPosition() {
        return position;
    }

    public Employee position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Business getBusiness() {
        return business;
    }

    public Employee business(Business business) {
        this.business = business;
        return this;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", employeeSecurityNumber='" + getEmployeeSecurityNumber() + "'" +
            ", employeeEmergencyNumber='" + getEmployeeEmergencyNumber() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", employeeZipAddress='" + getEmployeeZipAddress() + "'" +
            ", employeeAddress='" + getEmployeeAddress() + "'" +
            ", employeeDetailAddress='" + getEmployeeDetailAddress() + "'" +
            ", employeeStartDate='" + getEmployeeStartDate() + "'" +
            ", employeeLeaveDate='" + getEmployeeLeaveDate() + "'" +
            "}";
    }
}
