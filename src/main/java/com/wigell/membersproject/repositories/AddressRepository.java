package com.wigell.membersproject.repositories;

import com.wigell.membersproject.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByStreetAndPostalCodeAndCity(String street, String postalCode, String city);
}
