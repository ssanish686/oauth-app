package com.anish.dto.request;

import com.anish.util.converter.ToLowerCase;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserCreationRequestDto {

    @NotBlank(message = "UserName must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$",
            message = "UserName must be of 6 length with no special characters" )
    @ToLowerCase
    private String userName;
    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,}$",
            message = "Password must be min 10 length, containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;
    @NotEmpty(message = "Roles must not be empty")
    private List<String> roles;
}
