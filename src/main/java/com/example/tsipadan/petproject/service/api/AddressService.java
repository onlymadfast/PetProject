package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.AddressDTO;
import com.example.tsipadan.petproject.model.Address;

import java.util.UUID;

public interface AddressService {

    //получение адресы для отображения в личном кабинете у конкретного юзера
    AddressDTO getAddressFromUser(UUID id);

    //создание адреса для юзера
    Address createOrUpdateAddressForUser(UUID id, Address entity);

    //удаление адреса у юзера
    String deleteAddressByUserId(UUID id);

    boolean isUserHaveAddress(UUID id);

}
