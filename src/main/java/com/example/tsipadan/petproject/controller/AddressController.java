package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.AddressDTO;
import com.example.tsipadan.petproject.model.Address;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.service.api.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{userId}")
    private AddressDTO getAddressFromUser(@PathVariable UUID userId) {
        return addressService.getAddressFromUser(userId);
    }

    @PostMapping("/{userId}")
    private Address createOrUpdateAddressForUser(@PathVariable UUID userId,
                                                 @RequestBody Address entity) {
        return addressService.createOrUpdateAddressForUser(userId, entity);
    }

    @DeleteMapping("/{userId}")
    private Response deleteUserAddress(@PathVariable UUID userId) {
        return addressService.deleteAddressByUserId(userId);
    }

}
