package com.corcino.medical.json;

import com.corcino.medical.entity.Patient;
import lombok.Data;

@Data
public class PatientResponseList {

    private Long patientId;
    private String name;
    private String email;
    private String cpf;

    public PatientResponseList(Patient patient) {
        this.patientId = patient.getPatientId();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.cpf = patient.getCpf();
    }

}
