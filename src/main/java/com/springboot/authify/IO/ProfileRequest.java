package com.springboot.authify.IO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {
    @NotBlank(message = "Name cannot be blank")
    public String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be null")
    public String email;
    @Size(min = 6, message = "Password should be at least 6 characters long")
    public String password;
    public String role;
}