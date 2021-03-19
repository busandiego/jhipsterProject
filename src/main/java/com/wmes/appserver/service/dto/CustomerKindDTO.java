package com.wmes.appserver.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.CustomerKind} entity.
 */
public class CustomerKindDTO implements Serializable {

    private Long id;

    private String customerKindName;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerKindName() {
        return customerKindName;
    }

    public void setCustomerKindName(String customerKindName) {
        this.customerKindName = customerKindName;
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

        CustomerKindDTO customerKindDTO = (CustomerKindDTO) o;
        if (customerKindDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerKindDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerKindDTO{" +
            "id=" + getId() +
            ", customerKindName='" + getCustomerKindName() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
