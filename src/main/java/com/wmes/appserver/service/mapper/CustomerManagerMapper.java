package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.CustomerManagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerManager} and its DTO {@link CustomerManagerDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustomerManagerMapper extends EntityMapper<CustomerManagerDTO, CustomerManager> {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerManagerDTO toDto(CustomerManager customerManager);

    @Mapping(source = "customerId", target = "customer")
    CustomerManager toEntity(CustomerManagerDTO customerManagerDTO);

    default CustomerManager fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerManager customerManager = new CustomerManager();
        customerManager.setId(id);
        return customerManager;
    }
}
