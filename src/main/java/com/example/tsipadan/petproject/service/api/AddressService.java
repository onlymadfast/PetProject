package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.AddressDTO;
import com.example.tsipadan.petproject.model.Address;
import com.example.tsipadan.petproject.model.Response;

import java.util.UUID;

public interface AddressService {

    /**
     * Get address from user
     *
     * @param userId - user identifier
     * @return - return address dto
     */
    AddressDTO getAddressFromUser(UUID userId);

    /**
     * Create or update address from specific user
     *
     * @param userId - user identifier
     * @param entity - address object to create
     * @return representation of result operation
     */
    Address createOrUpdateAddressForUser(UUID userId, Address entity);

    /**
     * Delete address from specific user
     *
     * @param userId - user identifier
     * @return representation of result operation
     */
    Response deleteAddressByUserId(UUID userId);

}
