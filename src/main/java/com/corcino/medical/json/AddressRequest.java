package com.corcino.medical.json;

import lombok.Data;

@Data
public class AddressRequest {

    private String addressLine;
    private String cep;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;

}
