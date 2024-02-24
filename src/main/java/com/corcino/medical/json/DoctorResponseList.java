package com.corcino.medical.json;

import com.corcino.medical.entity.Doctor;
import com.corcino.medical.entity.Expertise;
import lombok.Data;

@Data
public class DoctorResponseList {

    private Long doctorId;
    private String name;
    private String email;
    private String crm;
    private Expertise expertise;

    public DoctorResponseList(Doctor doctor) {
        this.doctorId = doctor.getDoctorId();
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.crm = doctor.getCrm();
        this.expertise = doctor.getExpertise();
    }

}
