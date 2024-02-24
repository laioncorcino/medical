package com.corcino.medical.json;

import lombok.Data;

@Data
public class UpdateDoctorRequest {

    private String name;
    private String email;
    private String crm;
    private String phone;
    private String expertise;
    private UpdateAddressRequest address;

}
