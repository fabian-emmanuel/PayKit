package com.codewithfibbee.paykit.payloads.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
    private String accessToken;//todo remove redundant
    private String userName;
}
