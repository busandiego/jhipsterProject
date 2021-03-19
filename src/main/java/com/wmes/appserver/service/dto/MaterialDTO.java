package com.wmes.appserver.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.Material} entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    private String materialName;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

        MaterialDTO materialDTO = (MaterialDTO) o;
        if (materialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", materialName='" + getMaterialName() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
