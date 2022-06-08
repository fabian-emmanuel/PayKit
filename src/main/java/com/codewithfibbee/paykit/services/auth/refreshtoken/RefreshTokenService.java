package com.codewithfibbee.paykit.services.auth.refreshtoken;



import com.codewithfibbee.paykit.models.users.token.UserRefreshToken;
import com.codewithfibbee.paykit.payloads.auth.request.RefreshTokenRequest;

import java.util.Optional;

public interface RefreshTokenService {

//    String refreshToken(RefreshTokenRequest refreshTokenRequest);

    UserRefreshToken save(UserRefreshToken refreshToken);

    Optional<UserRefreshToken> findByToken(String token);

    void deleteToken(UserRefreshToken refreshToken);

    String refreshAdminUserToken(RefreshTokenRequest refreshTokenRequest);

//    String refreshClientToken(RefreshTokenRequest refreshTokenRequest);
}
