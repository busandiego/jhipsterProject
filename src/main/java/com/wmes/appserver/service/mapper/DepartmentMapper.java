package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {


    @Mapping(target = "departmentDetails", ignore = true)
    @Mapping(target = "removeDepartmentDetails", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
