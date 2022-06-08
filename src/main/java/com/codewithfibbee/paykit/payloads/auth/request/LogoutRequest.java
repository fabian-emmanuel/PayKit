package com.codewithfibbee.paykit.payloads.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogoutRequest {
    private String refreshToken;
}
