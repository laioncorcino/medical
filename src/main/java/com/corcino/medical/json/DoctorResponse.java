package com.corcino.medical.json;

import com.corcino.medical.entity.Doctor;
import com.corcino.medical.entity.Expertise;
import lombok.Data;

@Data
public class DoctorResponse {

    private Long doctorId;
    private String name;
    private String email;
    private String crm;
    private String phone;
    private Expertise expertise;
    private AddressResponse address;

    public DoctorResponse(Doctor doctor) {
        this.doctorId = doctor.getDoctorId();
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.crm = doctor.getCrm();
        this.phone = doctor.getPhone();
        this.expertise = doctor.getExpertise();
        this.address = new AddressResponse(doctor.getAddress());
    }
}
