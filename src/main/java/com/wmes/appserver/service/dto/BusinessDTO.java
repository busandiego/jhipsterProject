package com.wmes.appserver.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.Business} entity.
 */
public class BusinessDTO implements Serializable {

    private Long id;

    @NotNull
    private String businessName;

    @NotNull
    private String businessRepresentative;

    private String businessRegistrationNum;

    private String businessType;

    private String businessCategory;

    private String businessRepresentativeNum;



 //   private BusinessPlaceDTO businessPlaceDTO;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessRepresentative() {
        return businessRepresentative;
    }

    public void setBusinessRepresentative(String businessRepresentative) {
        this.businessRepresentative = businessRepresentative;
    }

    public String getBusinessRegistrationNum() {
        return businessRegistrationNum;
    }

    public void setBusinessRegistrationNum(String businessRegistrationNum) {
        this.businessRegistrationNum = businessRegistrationNum;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessRepresentativeNum() {
        return businessRepresentativeNum;
    }

    public void setBusinessRepresentativeNum(String businessRepresentativeNum) {
        this.businessRepresentativeNum = businessRepresentativeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessDTO businessDTO = (BusinessDTO) o;
        if (businessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessDTO{" +
            "id=" + id +
            ", businessName='" + businessName + '\'' +
            ", businessRepresentative='" + businessRepresentative + '\'' +
            ", businessRegistrationNum='" + businessRegistrationNum + '\'' +
            ", businessType='" + businessType + '\'' +
            ", businessCategory='" + businessCategory + '\'' +
            ", businessRepresentativeNum='" + businessRepresentativeNum + '\'' +
            '}';
    }
}
