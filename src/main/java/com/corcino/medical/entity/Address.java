package com.corcino.medical.entity;

import com.corcino.medical.json.AddressRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Address {

    private String addressLine;
    private String cep;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;

    public Address(AddressRequest addressRequest) {
        this.addressLine = addressRequest.getAddressLine();
        this.cep = addressRequest.getCep();
        this.number = addressRequest.getNumber();
        this.complement = addressRequest.getComplement();
        this.district = addressRequest.getDistrict();
        this.city = addressRequest.getCity();
        this.state = addressRequest.getState();
    }
}
