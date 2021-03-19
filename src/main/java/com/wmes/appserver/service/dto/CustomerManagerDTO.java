package com.wmes.appserver.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.CustomerManager} entity.
 */
public class CustomerManagerDTO implements Serializable {

    private Long id;

    private String customerManagerName;

    private String customerManagerDepartment;

    private String customerManagerNumber;

    private String customerMangerEmail;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerManagerName() {
        return customerManagerName;
    }

    public void setCustomerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
    }

    public String getCustomerManagerDepartment() {
        return customerManagerDepartment;
    }

    public void setCustomerManagerDepartment(String customerManagerDepartment) {
        this.customerManagerDepartment = customerManagerDepartment;
    }

    public String getCustomerManagerNumber() {
        return customerManagerNumber;
    }

    public void setCustomerManagerNumber(String customerManagerNumber) {
        this.customerManagerNumber = customerManagerNumber;
    }

    public String getCustomerMangerEmail() {
        return customerMangerEmail;
    }

    public void setCustomerMangerEmail(String customerMangerEmail) {
        this.customerMangerEmail = customerMangerEmail;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerManagerDTO customerManagerDTO = (CustomerManagerDTO) o;
        if (customerManagerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerManagerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerManagerDTO{" +
            "id=" + getId() +
            ", customerManagerName='" + getCustomerManagerName() + "'" +
            ", customerManagerDepartment='" + getCustomerManagerDepartment() + "'" +
            ", customerManagerNumber='" + getCustomerManagerNumber() + "'" +
            ", customerMangerEmail='" + getCustomerMangerEmail() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
