package com.corcino.medical.json;

import com.corcino.medical.entity.Address;
import lombok.Data;

@Data
public class AddressResponse {

    private String addressLine;
    private String cep;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;

    public AddressResponse(Address address) {
        this.addressLine = address.getAddressLine();
        this.cep = address.getCep();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.district = address.getDistrict();
        this.city = address.getCity();
        this.state = address.getState();
    }
}
