package com.corcino.medical.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

}
