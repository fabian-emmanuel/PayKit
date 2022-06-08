package com.codewithfibbee.paykit.payloads.auth;

import lombok.Data;

@Data
public class VerifyTokenDto {
    private String token;
}
