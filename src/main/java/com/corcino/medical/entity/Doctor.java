package com.corcino.medical.entity;

import com.corcino.medical.json.DoctorRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String name;
    private String email;
    private String crm;
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private Expertise expertise;

    @Embedded
    private Address address;

    public Doctor(DoctorRequest doctorRequest) {
        this.name = doctorRequest.getName();
        this.email = doctorRequest.getEmail();
        this.crm = doctorRequest.getCrm();
        this.phone = doctorRequest.getPhone();
        this.expertise = Expertise.valueOf(doctorRequest.getExpertise());
        this.address = new Address(doctorRequest.getAddress());
    }
}
