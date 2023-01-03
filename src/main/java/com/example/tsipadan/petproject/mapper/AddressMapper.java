package com.example.tsipadan.petproject.mapper;

import com.example.tsipadan.petproject.dto.AddressDTO;
import com.example.tsipadan.petproject.model.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AddressMapper {

    private final ModelMapper mapper;

    //convert Entity to DTO
    public AddressDTO mapToDTO(Address address) {
        return mapper.map(address, AddressDTO.class);
    }

    //convert DTO to Entity
    public Address mapToEntity(AddressDTO addressDTO) {
        return mapper.map(addressDTO, Address.class);
    }

}
