package com.codewithfibbee.paykit.payloads.auth.response;

import com.codewithfibbee.paykit.payloads.auth.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto{
    private UserDto user;
    private String token;
    private String refreshToken;
    private Collection<String> authorities;

    public LoginResponseDto(UserDto userDto, String token) {
        this.user = userDto;
        this.token=token;
    }
}
