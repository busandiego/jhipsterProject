package com.wmes.appserver.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private String customerName;

    @NotNull
    private String customerRepresentative;

    private String customerRegistrationNum;

    private String customerRepresentativeNum;

    private String customerType;

    private String customerCategory;

    private String customerNumber;

    private String customerEmail;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerRepresentative() {
        return customerRepresentative;
    }

    public void setCustomerRepresentative(String customerRepresentative) {
        this.customerRepresentative = customerRepresentative;
    }

    public String getCustomerRegistrationNum() {
        return customerRegistrationNum;
    }

    public void setCustomerRegistrationNum(String customerRegistrationNum) {
        this.customerRegistrationNum = customerRegistrationNum;
    }

    public String getCustomerRepresentativeNum() {
        return customerRepresentativeNum;
    }

    public void setCustomerRepresentativeNum(String customerRepresentativeNum) {
        this.customerRepresentativeNum = customerRepresentativeNum;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (customerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerRepresentative='" + getCustomerRepresentative() + "'" +
            ", customerRegistrationNum='" + getCustomerRegistrationNum() + "'" +
            ", customerRepresentativeNum='" + getCustomerRepresentativeNum() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            ", customerCategory='" + getCustomerCategory() + "'" +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", customerEmail='" + getCustomerEmail() + "'" +
            "}";
    }
}
