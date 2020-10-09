package com.whoiscan.userregistertest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @Pattern(regexp = "[a-zA-Z0-9.\\\\-_]{5,32}", message = "Username must be between 5 and 32 characters. Username must be alpha numerical.")
    private String username;

    @NotBlank(message = "Enter email address")
    @Email(message = "Enter valid email address")
    private String email;

    @Size(min = 3, message = "Full name must be minimum 3 characters.")
    private String fullName;

    @Size(min = 5, max = 16, message = "Password must be between 5 and 16 characters.")

    private String password;

    private String prePassword;

    @AssertTrue(message="Password is not confirmed.")
    private boolean isValid() {
        return this.password.equals(this.prePassword);
    }

}
