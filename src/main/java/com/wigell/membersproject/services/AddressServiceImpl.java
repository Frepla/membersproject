package com.wigell.membersproject.services;

import com.wigell.membersproject.entities.Address;
import com.wigell.membersproject.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address findAddressByStreetPostalCodeAndCity(String street, String postalCode, String city) {
        return addressRepository.findByStreetAndPostalCodeAndCity(street, postalCode, city);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
