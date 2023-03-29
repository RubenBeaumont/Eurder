package com.switchfully.eurder.user.service.mapper;

import com.switchfully.eurder.user.api.dto.itemDTO.ItemDTO;
import com.switchfully.eurder.user.domain.itemObject.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDTO toDTO(Item item){
        return new ItemDTO(item);
    }
}
