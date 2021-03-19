package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.CustomerKindDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerKind} and its DTO {@link CustomerKindDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustomerKindMapper extends EntityMapper<CustomerKindDTO, CustomerKind> {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerKindDTO toDto(CustomerKind customerKind);

    @Mapping(source = "customerId", target = "customer")
    CustomerKind toEntity(CustomerKindDTO customerKindDTO);

    default CustomerKind fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerKind customerKind = new CustomerKind();
        customerKind.setId(id);
        return customerKind;
    }
}
