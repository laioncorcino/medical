package com.corcino.medical.json;

import lombok.Data;

@Data
public class DoctorRequest {

    private String name;
    private String email;
    private String crm;
    private String expertise;
    private AddressRequest address;

}
