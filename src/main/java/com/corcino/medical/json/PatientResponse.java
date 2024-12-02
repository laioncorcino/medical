package com.corcino.medical.json;

import com.corcino.medical.entity.Patient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class PatientResponse {

    private Long patientId;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private AddressResponse address;

    public PatientResponse(Patient patient) {
        this.patientId = patient.getPatientId();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.cpf = patient.getCpf();
        this.address = new AddressResponse(patient.getAddress());
    }

}
