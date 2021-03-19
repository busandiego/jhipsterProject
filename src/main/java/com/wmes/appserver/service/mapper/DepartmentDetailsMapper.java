package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.DepartmentDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartmentDetails} and its DTO {@link DepartmentDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface DepartmentDetailsMapper extends EntityMapper<DepartmentDetailsDTO, DepartmentDetails> {

    @Mapping(source = "department.id", target = "departmentId")
    DepartmentDetailsDTO toDto(DepartmentDetails departmentDetails);

    @Mapping(target = "employee", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    DepartmentDetails toEntity(DepartmentDetailsDTO departmentDetailsDTO);

    default DepartmentDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepartmentDetails departmentDetails = new DepartmentDetails();
        departmentDetails.setId(id);
        return departmentDetails;
    }
}
