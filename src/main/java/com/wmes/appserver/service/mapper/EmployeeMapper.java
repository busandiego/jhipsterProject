package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, DepartmentDetailsMapper.class, PositionMapper.class, BusinessMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "departmentDetails.id", target = "departmentDetailsId")
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "business.id", target = "businessId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "departmentDetailsId", target = "departmentDetails")
    @Mapping(source = "positionId", target = "position")
    @Mapping(source = "businessId", target = "business")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
