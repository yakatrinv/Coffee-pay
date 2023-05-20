package com.coffeepay.repository;

import com.coffeepay.model.Address;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class AddressRepositoryTest {
    public static final String MINSK = "Minsk";
    public static final String LENINA = "Lenina";
    public static final int ONE = 1;
    public static final int THREE = 3;
    public static final String UPDATE_MINSK = "update MINSK";
    public static final String UPDATE_LENINA = "update Lenina";
    @Autowired
    AddressRepository addressRepository;

    @Test
    public void addressListIsEmpty() {
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).isEmpty();
    }

    @Test
    public void findAll() {
        Address newAddress1 = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        Address newAddress2 = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        Address newAddress3 = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(THREE);
        assertThat(addressList).contains(newAddress1,newAddress2,newAddress3);

        addressRepository.deleteAll();
    }

    @Test
    public void saveAddress() {
        Address newAddress = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        Optional<Address> address = Optional.ofNullable(newAddress.getId())
                .map(addressRepository::findById)
                .orElse(null);

        Assertions.assertAll(
                () -> assertEquals("In list not one element",addressRepository.findAll().size(), ONE),
                () -> assertNotNull("Address not save",newAddress.getId()),
                () -> assertNotNull("Address not found",address),
                () -> assertTrue("Address not found",address != null && address.isPresent()),
                () -> Assert.assertEquals(
                        "City is not equals",
                        (address != null && address.isPresent()) ? address.get().getCity() : null,
                        MINSK),
                () -> Assert.assertEquals(
                        "Street is not equals",
                        (address != null && address.isPresent()) ? address.get().getStreet() : null,
                        LENINA)
        );
        addressRepository.deleteAll();
    }

    @Test
    public void deleteAddressById() {
        Address newAddress = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        addressRepository.deleteById(newAddress.getId());
        assertThat(addressRepository.findAll()).isEmpty();
    }

    @Test
    public void findAddressById() {
        Address newAddress1 = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        Address newAddress2 = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        Optional<Address> address = Optional.ofNullable(newAddress2.getId())
                .map(addressRepository::findById)
                .orElse(null);

        Assertions.assertAll(
                () -> assertNotNull("Address not save",newAddress2.getId()),
                () -> assertNotNull("Address not found",address),
                () -> assertTrue("Address not found",address != null && address.isPresent()),
                () -> Assert.assertEquals(
                        "Id is not equals",
                        (address != null && address.isPresent()) ? address.get().getId() : null,
                        newAddress2.getId()),
                () -> Assert.assertEquals(
                        "City is not equals",
                        (address != null && address.isPresent()) ? address.get().getCity() : null,
                        MINSK),
                () -> Assert.assertEquals(
                        "Street is not equals",
                        (address != null && address.isPresent()) ? address.get().getStreet() : null,
                        LENINA)
        );
        addressRepository.deleteAll();
    }

    @Test
    public void updateAddress() {
        Address newAddress = addressRepository.save(
                Address.builder()
                        .city(MINSK)
                        .street(LENINA)
                        .build()
        );

        newAddress.setCity(UPDATE_MINSK);
        newAddress.setStreet(UPDATE_LENINA);
        addressRepository.save(newAddress);

        Optional<Address> address = Optional.ofNullable(newAddress.getId())
                .map(addressRepository::findById)
                .orElse(null);

        Assertions.assertAll(
                () -> assertEquals("In list not one element",addressRepository.findAll().size(), ONE),
                () -> assertNotNull("Address not save",newAddress.getId()),
                () -> assertNotNull("Address not found",address),
                () -> assertTrue("Address not found",address != null && address.isPresent()),
                () -> Assert.assertEquals(
                        "City is not equals",
                        (address != null && address.isPresent()) ? address.get().getCity() : null,
                        UPDATE_MINSK),
                () -> Assert.assertEquals(
                        "Street is not equals",
                        (address != null && address.isPresent()) ? address.get().getStreet() : null,
                        UPDATE_LENINA)
        );
        addressRepository.deleteAll();
    }
}