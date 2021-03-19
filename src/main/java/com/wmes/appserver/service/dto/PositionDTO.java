package com.wmes.appserver.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmes.appserver.domain.Position} entity.
 */
public class PositionDTO implements Serializable {

    private Long id;

    private String positionCode;

    private String positionName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PositionDTO positionDTO = (PositionDTO) o;
        if (positionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), positionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
            "id=" + getId() +
            ", positionCode='" + getPositionCode() + "'" +
            ", positionName='" + getPositionName() + "'" +
            "}";
    }
}
