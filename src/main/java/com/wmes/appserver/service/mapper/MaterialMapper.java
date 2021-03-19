package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Material} and its DTO {@link MaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {

    @Mapping(source = "customer.id", target = "customerId")
    MaterialDTO toDto(Material material);

    @Mapping(source = "customerId", target = "customer")
    Material toEntity(MaterialDTO materialDTO);

    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
