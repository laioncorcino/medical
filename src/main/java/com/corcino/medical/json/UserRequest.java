package com.corcino.medical.json;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
