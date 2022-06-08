package com.codewithfibbee.paykit.services.auth;


import com.codewithfibbee.paykit.payloads.auth.request.LogoutRequest;
import com.codewithfibbee.paykit.payloads.auth.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(String username, String password);
    void logout(String token, LogoutRequest logoutRequest);
}
