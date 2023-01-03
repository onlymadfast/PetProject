package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.mapper.AddressMapper;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.repository.AddressRepository;
import com.example.tsipadan.petproject.repository.UserRepository;
import com.example.tsipadan.petproject.service.api.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressMapper addressMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;


    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressServiceImpl(addressMapper,
                userMapper, addressRepository, userRepository);
    }

    @Test
    void getAddressFromUser() {
    }

    @Test
    void createAddressForUser() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteUserAddressByUsername() {
    }
}