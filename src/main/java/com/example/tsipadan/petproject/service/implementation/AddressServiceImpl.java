package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.dto.AddressDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.exception.EntityNotFoundException;
import com.example.tsipadan.petproject.mapper.AddressMapper;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.model.Address;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.repository.AddressRepository;
import com.example.tsipadan.petproject.service.api.AddressService;
import com.example.tsipadan.petproject.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Override
    public AddressDTO getAddressFromUser(UUID userId) {
        log.info("Get address for user < {} >", userId);
        UserDTO userDTO = userService.findUserById(userId);
        User user = userMapper.mapToEntity(userDTO);
        return addressMapper.mapToDTO(user.getAddress());
    }

    @Override
    public Address createOrUpdateAddressForUser(UUID userId, Address entity) {
        UserDTO userDTO = userService.findUserById(userId);
        User user = userMapper.mapToEntity(userDTO);
        Address address = addressRepository.save(setAddress(entity));
        user.setAddress(address);
        User u = userService.saveUser(user);
        log.info("Creating address < {} > for user < {} > ", address.getId(), u.getId());
        return address;
    }

    @Override
    public Response deleteAddressByUserId(UUID userId) {
        UserDTO userDTO = userService.findUserById(userId);
        User user = userMapper.mapToEntity(userDTO);
        if (user.getAddress().getId() != null) {
            addressRepository.deleteById(user.getAddress().getId());
            user.setAddress(null);
            userService.saveUser(user);
        } else
            throw new EntityNotFoundException("Address id from user is null");
        log.info("Delete address by userId < {} >", userId);
        return Response.builder()
                .localDateTime(LocalDateTime.now())
                .message("Successfully deleted address from user < " + userId + " >")
                .build();
    }

    private boolean isUserHaveAddress(UUID id) {
        return userService.findUserById(id).getAddress() != null;
    }

    private Address setAddress(Address entity) {
        Address address = new Address();
        address.setCountry(entity.getCountry());
        address.setCity(entity.getCity());
        address.setZip(entity.getZip());
        address.setStreet(entity.getStreet());
        address.setHouse(entity.getHouse());
        address.setApartment(entity.getApartment());
        return address;
    }

}
