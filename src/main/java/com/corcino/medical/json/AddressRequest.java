package com.corcino.medical.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank
    private String addressLine;

    @NotBlank
    @Pattern(regexp = "\\d{8}")
    private String cep;

    private Integer number;
    private String complement;

    @NotBlank
    private String district;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

}
