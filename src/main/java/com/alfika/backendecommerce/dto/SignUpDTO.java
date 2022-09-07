package com.alfika.backendecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
public class SignUpDTO {

    @NotBlank @Size(min = 5, max = 20)
    private String username;

    @NotBlank @Size(min = 5, max = 60)
    private String password;

    @NotBlank @Email
    private String email;

    private Set<String> role;

    private String address;
}
