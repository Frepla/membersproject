package com.wigell.membersproject.services;

import com.wigell.membersproject.entities.Address;

public interface AddressService {

    Address findAddressByStreetPostalCodeAndCity(String street, String postalCode, String city);
    Address saveAddress(Address address);
}
