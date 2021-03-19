package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {


    @Mapping(target = "businessPlaces", ignore = true)
    @Mapping(target = "removeBusinessPlace", ignore = true)
    @Mapping(target = "customerManagers", ignore = true)
    @Mapping(target = "removeCustomerManager", ignore = true)
    @Mapping(target = "customerKinds", ignore = true)
    @Mapping(target = "removeCustomerKind", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "removeItem", ignore = true)
    @Mapping(target = "materials", ignore = true)
    @Mapping(target = "removeMaterial", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
