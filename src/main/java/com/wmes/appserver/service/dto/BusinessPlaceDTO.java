package com.wmes.appserver.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.BusinessPlace} entity.
 */
public class BusinessPlaceDTO implements Serializable {

    private Long id;

    @NotNull
    private String bpName;

    private Boolean isHeadquarter;

    private String bpZipAddress;

    private String bpAddress;

    private String bpDetailAddress;

    private String bpFaxNumber;

    private String bpNumber;


    private Long businessId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Boolean isIsHeadquarter() {
        return isHeadquarter;
    }

    public void setIsHeadquarter(Boolean isHeadquarter) {
        this.isHeadquarter = isHeadquarter;
    }

    public String getBpZipAddress() {
        return bpZipAddress;
    }

    public void setBpZipAddress(String bpZipAddress) {
        this.bpZipAddress = bpZipAddress;
    }

    public String getBpAddress() {
        return bpAddress;
    }

    public void setBpAddress(String bpAddress) {
        this.bpAddress = bpAddress;
    }

    public String getBpDetailAddress() {
        return bpDetailAddress;
    }

    public void setBpDetailAddress(String bpDetailAddress) {
        this.bpDetailAddress = bpDetailAddress;
    }

    public String getBpFaxNumber() {
        return bpFaxNumber;
    }

    public void setBpFaxNumber(String bpFaxNumber) {
        this.bpFaxNumber = bpFaxNumber;
    }

    public String getBpNumber() {
        return bpNumber;
    }

    public void setBpNumber(String bpNumber) {
        this.bpNumber = bpNumber;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

        BusinessPlaceDTO businessPlaceDTO = (BusinessPlaceDTO) o;
        if (businessPlaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessPlaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessPlaceDTO{" +
            "id=" + getId() +
            ", bpName='" + getBpName() + "'" +
            ", isHeadquarter='" + isIsHeadquarter() + "'" +
            ", bpZipAddress='" + getBpZipAddress() + "'" +
            ", bpAddress='" + getBpAddress() + "'" +
            ", bpDetailAddress='" + getBpDetailAddress() + "'" +
            ", bpFaxNumber='" + getBpFaxNumber() + "'" +
            ", bpNumber='" + getBpNumber() + "'" +
            ", business=" + getBusinessId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
