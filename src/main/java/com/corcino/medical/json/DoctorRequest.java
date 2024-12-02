package com.corcino.medical.json;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{4,6}")
    private String crm;

    @NotBlank
    private String phone;

    @NotNull
    private String expertise;

    @NotNull
    @Valid
    private AddressRequest address;

}
