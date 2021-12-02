package mz.co.muianga.service;

import javax.enterprise.context.ApplicationScoped;
import mz.co.muianga.model.Address;
import mz.co.muianga.resources.dto.AddressDto;

@ApplicationScoped
public class AddressService {

    public static Address createFromDto(AddressDto addressDto) {
        return new Address(addressDto.getAddress1(),
                addressDto.getAddress2(),
                addressDto.getCity(),
                addressDto.getPostcode(),
                addressDto.getCountry());
    }

    public static AddressDto mapToDto(Address address) {
        return new AddressDto(address.getAddres1(),
                address.getAddres2(),
                address.getCity(),
                address.getPostcode(),
                address.getCountry());
    }
}
