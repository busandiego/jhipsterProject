package com.wmes.appserver.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.DepartmentDetails} entity.
 */
public class DepartmentDetailsDTO implements Serializable {

    private Long id;

    private String departmentDetailsName;


    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentDetailsName() {
        return departmentDetailsName;
    }

    public void setDepartmentDetailsName(String departmentDetailsName) {
        this.departmentDetailsName = departmentDetailsName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepartmentDetailsDTO departmentDetailsDTO = (DepartmentDetailsDTO) o;
        if (departmentDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departmentDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepartmentDetailsDTO{" +
            "id=" + getId() +
            ", departmentDetailsName='" + getDepartmentDetailsName() + "'" +
            ", department=" + getDepartmentId() +
            "}";
    }
}
