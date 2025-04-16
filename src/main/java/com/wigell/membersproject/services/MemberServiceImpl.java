package com.wigell.membersproject.services;

import com.wigell.membersproject.entities.Address;
import com.wigell.membersproject.entities.Member;
import com.wigell.membersproject.exceptions.InvalidInputException;
import com.wigell.membersproject.exceptions.ResourceNotFoundException;
import com.wigell.membersproject.exceptions.InvalidFormatException;
import com.wigell.membersproject.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private static final String NAME_REGEX = "^[a-zA-ZåäöÅÄÖ]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+?\\d{1,3}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";
    private static final String STREET_REGEX = "^[A-Za-zÅÄÖåäö]{2,}(\\s[A-Za-zÅÄÖåäö]+)*\\s\\d+[A-Za-z]?$";
    private static final String POSTAL_CODE_REGEX = "^\\d{5}$";

    private final AddressService addressService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(AddressService addressService, MemberRepository memberRepository) {
        this.addressService= addressService;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }

    @Override
    public void deleteMemberById(long id) {
        memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
        memberRepository.deleteById(id);
    }

    @Override
    public Member addMember(Member member) {
        validateMemberFields(member);

        Address address = member.getAddress();
        Address existingAddress = addressService.findAddressByStreetPostalCodeAndCity(
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );

        if (existingAddress != null) {
            member.setAddress(existingAddress);
        } else {
            address.setId(null);
            member.setAddress(addressService.saveAddress(address));
        }

        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long id, Member updatedMember) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));

        validateMemberFields(updatedMember);
        updateMemberFields(existingMember, updatedMember);

        if (updatedMember.getAddress() != null) {
            existingMember.setAddress(updateOrCreateAddress(updatedMember.getAddress()));
        }

        return memberRepository.save(existingMember);
    }

    private void updateMemberFields(Member existingMember, Member updatedMember) {
        existingMember.setFirstName(updatedMember.getFirstName());
        existingMember.setLastName(updatedMember.getLastName());
        existingMember.setPhone(updatedMember.getPhone());
        existingMember.setDateOfBirth(updatedMember.getDateOfBirth());
        existingMember.setEmail(updatedMember.getEmail());
    }

    private Address updateOrCreateAddress(Address updatedAddress) {
        Address addressInDb = addressService.findAddressByStreetPostalCodeAndCity(
                updatedAddress.getStreet(),
                updatedAddress.getPostalCode(),
                updatedAddress.getCity()
        );

        if (addressInDb != null) {
            return addressInDb;
        } else {
            Address newAddress = new Address();
            newAddress.setStreet(updatedAddress.getStreet());
            newAddress.setPostalCode(updatedAddress.getPostalCode());
            newAddress.setCity(updatedAddress.getCity());
            newAddress.setId(null);
            return addressService.saveAddress(newAddress);
        }
    }

    private void validateMemberFields(Member member) {
        if (member == null) {
            throw new InvalidInputException("Member", "member", null);
        }

        validateNotBlank("Member", "firstName", member.getFirstName());
        validateFormat("Member", "firstName", member.getFirstName(), NAME_REGEX);

        validateNotBlank("Member", "lastName", member.getLastName());
        validateFormat("Member", "lastName", member.getLastName(), NAME_REGEX);

        validateNotBlank("Member", "email", member.getEmail());
        validateFormat("Member", "email", member.getEmail(), EMAIL_REGEX);

        String phone = member.getPhone();
        if (phone != null && !phone.isBlank()) {
            validateFormat("Member", "phone", phone, PHONE_REGEX);
        }

        if (member.getDateOfBirth() == null) {
            throw new InvalidInputException("Member", "dateOfBirth", null);
        }
        if (member.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new InvalidInputException("Member", "dateOfBirth", member.getDateOfBirth().toString());
        }

        validateAddress(member.getAddress());
    }

    private void validateAddress(Address address) {
        if (address == null) {
            throw new InvalidInputException("Member", "address", null);
        }

        validateNotBlank("Address", "street", address.getStreet());
        validateFormat("Address", "street", address.getStreet(), STREET_REGEX);

        validateNotBlank("Address", "postalCode", address.getPostalCode());
        validateFormat("Address", "postalCode", address.getPostalCode(), POSTAL_CODE_REGEX);

        validateNotBlank("Address", "city", address.getCity());
    }

    private void validateNotBlank(String member, String field, String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException(member, field, value);
        }
    }

    private void validateFormat(String member, String field, String value, String regex) {
        if (!value.matches(regex)) {
            throw new InvalidFormatException(member, field, value);
        }
    }
}