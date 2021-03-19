package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.BusinessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Business} and its DTO {@link BusinessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessMapper extends EntityMapper<BusinessDTO, Business> {


    @Mapping(target = "businessPlaces", ignore = true)
    @Mapping(target = "removeBusinessPlace", ignore = true)
    Business toEntity(BusinessDTO businessDTO);

    default Business fromId(Long id) {
        if (id == null) {
            return null;
        }
        Business business = new Business();
        business.setId(id);
        return business;
    }
}
