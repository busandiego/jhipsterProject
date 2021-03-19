package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.BusinessPlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessPlace} and its DTO {@link BusinessPlaceDTO}.
 */
@Mapper(componentModel = "spring", uses = {BusinessMapper.class, CustomerMapper.class})
public interface BusinessPlaceMapper extends EntityMapper<BusinessPlaceDTO, BusinessPlace> {

    @Mapping(source = "business.id", target = "businessId")
    @Mapping(source = "customer.id", target = "customerId")
    BusinessPlaceDTO toDto(BusinessPlace businessPlace);

    @Mapping(source = "businessId", target = "business")
    @Mapping(source = "customerId", target = "customer")
    BusinessPlace toEntity(BusinessPlaceDTO businessPlaceDTO);

    default BusinessPlace fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessPlace businessPlace = new BusinessPlace();
        businessPlace.setId(id);
        return businessPlace;
    }
}
