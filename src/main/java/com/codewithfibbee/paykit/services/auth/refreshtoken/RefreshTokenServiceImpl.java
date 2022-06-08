package com.codewithfibbee.paykit.services.auth.refreshtoken;


import com.codewithfibbee.paykit.configurations.security.AuthTokenProvider;
import com.codewithfibbee.paykit.configurations.security.admin.AdminUserDetailsService;
import com.codewithfibbee.paykit.configurations.security.admin.AdminUserInfo;
import com.codewithfibbee.paykit.enumtypes.UserType;
import com.codewithfibbee.paykit.exceptions.InvalidRefreshTokenException;
import com.codewithfibbee.paykit.models.users.token.CustomerRefreshToken;
import com.codewithfibbee.paykit.models.users.token.UserRefreshToken;
import com.codewithfibbee.paykit.payloads.auth.request.RefreshTokenRequest;
import com.codewithfibbee.paykit.repositories.token.CustomerRefreshTokenRepository;
import com.codewithfibbee.paykit.utils.CustomDateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final CustomerRefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;
    private final AdminUserDetailsService adminUserDetailsService;

//    @Override
//    public String refreshToken(RefreshTokenRequest refreshTokenRequest) {
//        this.validateToken(refreshTokenRequest);
//        return authTokenProvider.generateToken(refreshTokenRequest.getUserName(), UserType.CLIENT.name());
//    }


    private void validateToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<UserRefreshToken> optional = this.refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken());

        assert optional.isPresent() : "Token does not exist";

        assert !expiredToken(optional.get()) : "Token has expired";

        assert !invalidTokenIssued(optional.get(), refreshTokenRequest.getUserName()) : "Invalid Token issued";
    }

    private boolean invalidTokenIssued(UserRefreshToken refreshToken, String requestUserName) {
        return !StringUtils.equals(refreshToken.getUserName(), requestUserName);
    }

    @Override
    public UserRefreshToken save(UserRefreshToken refreshToken) {
        return this.refreshTokenRepository.save((CustomerRefreshToken) refreshToken);
    }

    @Override
    public Optional<UserRefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteToken(UserRefreshToken refreshToken) {
        this.refreshTokenRepository.delete((CustomerRefreshToken) refreshToken);
    }

    @Override
    public String refreshAdminUserToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            this.validateToken(refreshTokenRequest);
            AdminUserInfo userDetails = (AdminUserInfo) this.adminUserDetailsService.loadUserByUsername(refreshTokenRequest.getUserName());
            return authTokenProvider.generateToken(refreshTokenRequest.getUserName(), UserType.ADMIN.name());
        } catch (UsernameNotFoundException e) {
            throw new InvalidRefreshTokenException("Invalid username");
        }
    }

//    @Override
//    public String refreshClientToken(RefreshTokenRequest refreshTokenRequest) {
//        try {
//            this.validateToken(refreshTokenRequest);
//            VendorUserInfo userDetails = (VendorUserInfo) this.vendorUserDetailsService.loadUserByUsername(refreshTokenRequest.getUserName());
//            return authTokenProvider.generateToken(userDetails.getUsername(), UserType.VENDOR.name());
//        } catch (UsernameNotFoundException e) {
//            throw new InvalidRefreshTokenException("Invalid username");
//        }
//    }

    private boolean expiredToken(UserRefreshToken customerRefreshToken) {
        Date now = CustomDateUtils.now();
        return customerRefreshToken.getValidityTrm().before(now);
    }
}
