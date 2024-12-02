package com.corcino.medical.entity;

import com.corcino.medical.json.PatientRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    @Embedded
    private Address address;

    public Patient(PatientRequest patientRequest) {
        this.name = patientRequest.getName();
        this.email = patientRequest.getEmail();
        this.phone = patientRequest.getPhone();
        this.cpf = patientRequest.getCpf();
        this.address = new Address(patientRequest.getAddress());
    }

}
