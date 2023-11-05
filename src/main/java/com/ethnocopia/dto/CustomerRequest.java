package com.ethnocopia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "email must not be null")
    @Email
    private String email;

    @NotNull(message = "age must not be null")
    private Integer age;

}
