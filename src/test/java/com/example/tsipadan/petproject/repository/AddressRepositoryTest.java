package com.example.tsipadan.petproject.repository;

import com.example.tsipadan.petproject.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        List<Address> addresses = List.of(
                new Address(123L, "Russia", "Kazan",
                        356752, "Lenina", 25, 35),
                new Address(124L, "Russia", "Moscow",
                        356562, "Kamishovaya", 54, 98));
        addressRepository.saveAll(addresses);
    }

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    @DisplayName("Test Adding Address To Repository")
    void shouldReturnCorrectIdFromRepository() {
        Address address = new Address(153L, "Russia", "Chelyabins",
                354215, "Gradskaya", 1, 5);
        addressRepository.save(address);

        Address fetchedAddress = addressRepository.getById(address.getId());
        assertEquals(153L, fetchedAddress.getId());

    }

    @Test
    @DisplayName("Test Should Return All Addresses")
    void shouldReturnAllAddresses() {
        List<Address> addresses = List.of(
                new Address(123L, "Russia", "Kazan",
                        356752, "Tulskaya", 25, 35),
                new Address(124L, "Russia", "Moscow",
                        356562, "Vyatskaya", 54, 98));

        addressRepository.saveAll(addresses);
        List<Address> list = addressRepository.findAll();

        assertEquals(addresses.get(0).getStreet(), list.get(2).getStreet());
        assertEquals(addresses.get(1).getStreet(), list.get(3).getStreet());
    }

//    @Test
//    @DisplayName("Test Should Deleting Address")
//    void shouldDeletingAddress() {
//
//        Address address = new Address(564L, "Russia", "Chelyabinsk",
//                786354, "Yanskaya", 53, 88);
//        addressRepository.save(address);
//
//        addressRepository.deleteById(address.getId());
//        Optional<Address> optional = addressRepository.findById(address.getId());
//        assertEquals(Optional.empty(), optional);
//
//    }
}