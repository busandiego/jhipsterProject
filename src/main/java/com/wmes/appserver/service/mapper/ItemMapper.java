package com.wmes.appserver.service.mapper;

import com.wmes.appserver.domain.*;
import com.wmes.appserver.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "customer.id", target = "customerId")
    ItemDTO toDto(Item item);

    @Mapping(source = "customerId", target = "customer")
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
