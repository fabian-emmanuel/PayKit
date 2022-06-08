package com.codewithfibbee.paykit.payloads.auth.request;

import com.codewithfibbee.paykit.validators.password.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDto {
    @ValidPassword
    private String password;
    @ValidPassword
    private String confirmPassword;
}
