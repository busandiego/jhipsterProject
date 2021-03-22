package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.Business;
import com.wmes.appserver.domain.CustomerKind;
import com.wmes.appserver.service.dto.request.BusinessRequestDto;
import com.wmes.appserver.service.dto.request.BusinessResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Business} and its DTO {@link BusinessRequestDto}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessResponseDtoMapper extends EntityMapper<BusinessResponseDto, Business> {


    default Business fromId(Long id) {
        if (id == null) {
            return null;
        }
        Business business = new Business();
        business.setId(id);
        return business;
    }

}
