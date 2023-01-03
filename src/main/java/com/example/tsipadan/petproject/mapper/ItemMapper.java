package com.example.tsipadan.petproject.mapper;

import com.example.tsipadan.petproject.dto.ItemDTO;
import com.example.tsipadan.petproject.model.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ItemMapper {

    private final ModelMapper mapper;

    //convert Entity to DTO
    public ItemDTO mapToDTO(Item item) {
        return mapper.map(item, ItemDTO.class);
    }

    //convert DTO to Entity
    public Item mapToEntity(ItemDTO itemDTO) {
        return mapper.map(itemDTO, Item.class);
    }
}
