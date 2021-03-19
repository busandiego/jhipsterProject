package com.wmes.appserver.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    private String employeeName;

    private String employeeSecurityNumber;

    private String employeeEmergencyNumber;

    private String employeeNumber;

    private String employeeZipAddress;

    private String employeeAddress;

    private String employeeDetailAddress;

    private ZonedDateTime employeeStartDate;

    private ZonedDateTime employeeLeaveDate;


    private Long departmentId;

    private Long departmentDetailsId;

    private Long positionId;

    private Long businessId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSecurityNumber() {
        return employeeSecurityNumber;
    }

    public void setEmployeeSecurityNumber(String employeeSecurityNumber) {
        this.employeeSecurityNumber = employeeSecurityNumber;
    }

    public String getEmployeeEmergencyNumber() {
        return employeeEmergencyNumber;
    }

    public void setEmployeeEmergencyNumber(String employeeEmergencyNumber) {
        this.employeeEmergencyNumber = employeeEmergencyNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeZipAddress() {
        return employeeZipAddress;
    }

    public void setEmployeeZipAddress(String employeeZipAddress) {
        this.employeeZipAddress = employeeZipAddress;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeDetailAddress() {
        return employeeDetailAddress;
    }

    public void setEmployeeDetailAddress(String employeeDetailAddress) {
        this.employeeDetailAddress = employeeDetailAddress;
    }

    public ZonedDateTime getEmployeeStartDate() {
        return employeeStartDate;
    }

    public void setEmployeeStartDate(ZonedDateTime employeeStartDate) {
        this.employeeStartDate = employeeStartDate;
    }

    public ZonedDateTime getEmployeeLeaveDate() {
        return employeeLeaveDate;
    }

    public void setEmployeeLeaveDate(ZonedDateTime employeeLeaveDate) {
        this.employeeLeaveDate = employeeLeaveDate;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentDetailsId() {
        return departmentDetailsId;
    }

    public void setDepartmentDetailsId(Long departmentDetailsId) {
        this.departmentDetailsId = departmentDetailsId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
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
            ", department=" + getDepartmentId() +
            ", departmentDetails=" + getDepartmentDetailsId() +
            ", position=" + getPositionId() +
            ", business=" + getBusinessId() +
            "}";
    }
}
