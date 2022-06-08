package com.codewithfibbee.paykit.services.auth.admin;

import com.codewithfibbee.paykit.configurations.security.AuthTokenProvider;
import com.codewithfibbee.paykit.constants.PrcRsltCode;
import com.codewithfibbee.paykit.enumtypes.UserType;
import com.codewithfibbee.paykit.exceptions.AccountDisabledException;
import com.codewithfibbee.paykit.exceptions.CustomMadeException;
import com.codewithfibbee.paykit.exceptions.InvalidCredentialsException;
import com.codewithfibbee.paykit.models.users.admin.AdminLoginHistory;
import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import com.codewithfibbee.paykit.models.users.token.CustomerRefreshToken;
import com.codewithfibbee.paykit.models.users.token.UserRefreshToken;
import com.codewithfibbee.paykit.payloads.auth.request.LogoutRequest;
import com.codewithfibbee.paykit.payloads.auth.response.LoginResponseDto;
import com.codewithfibbee.paykit.repositories.user.AdminUserRepository;
import com.codewithfibbee.paykit.services.auth.AuthService;
import com.codewithfibbee.paykit.services.auth.refreshtoken.RefreshTokenService;
import com.codewithfibbee.paykit.services.auth.tokenblacklist.TokenBlacklistService;
import com.codewithfibbee.paykit.utils.AuthUtils;
import com.codewithfibbee.paykit.utils.CodeGeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.codewithfibbee.paykit.exceptions.CustomMadeException.*;

@Slf4j
@RequiredArgsConstructor
@Service("adminAuthService")
public class AdminAuthServiceImpl implements AuthService {


    private final AuthTokenProvider tokenProvider;
    private final AdminUserRepository repository;
    private final AdminLoginHistoryService loginHistoryService;
    private final AuthenticationManager adminAuthenticationManager;
    private final TokenBlacklistService tokenBlacklistService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponseDto login(String username, String password) {

        try {
            Authentication authentication = adminAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<AdminUser> user = repository.findByEmail(username);
            log.info(user.toString());

            if (user.isEmpty()) {
                throw new InvalidCredentialsException("User with supplied credential does not exist");
            }
            String token = tokenProvider.generateToken(authentication.getName(), UserType.ADMIN.name());
            if (isEmptyToken(token)) {
                log.error("Unable to generate token");
                throw  new UnAuthorizeException("invalid username/password:");
            }
            CustomerRefreshToken refreshToken = this.createRefreshTokenModel(user.get().getEmail());
            refreshToken = (CustomerRefreshToken) this.saveRefreshToken(refreshToken);

            //create login history
            this.createLoginHistory(user.get(), PrcRsltCode.SUCCESS, "");
            //create response
            LoginResponseDto authResponse = this.createLoginResponse(user.get(), token);
            authResponse.setAuthorities(AuthUtils.buildAuthorities(authentication));
            authResponse.setRefreshToken(refreshToken.getToken());
            return authResponse;

        } catch (Exception e) {
            e.printStackTrace();
            this.repository.findByEmail(username).ifPresent(user -> {
                this.createLoginHistory(user, PrcRsltCode.FAILURE, "");
            });
            if (e instanceof BadCredentialsException) {
                throw new InvalidCredentialsException("invalid username/password:");
            } else if (e instanceof DisabledException) {
                throw new AccountDisabledException("Account disabled");
            } else {
                throw e;
            }
        }
    }

    private UserRefreshToken saveRefreshToken(CustomerRefreshToken refreshToken) {
        return this.refreshTokenService.save(refreshToken);
    }

    private CustomerRefreshToken createRefreshTokenModel(String userName) {
        CustomerRefreshToken refreshToken = new CustomerRefreshToken();
        refreshToken.setUserName(userName);
        int validityTrm = 10080;//mins(7days) todo:should come from configuration
        refreshToken.calculateExpiryDate(String.valueOf(validityTrm));
        refreshToken.setToken(CodeGeneratorUtils.generateRefreshToken());
        return refreshToken;
    }

    @Override
    @Transactional
    public void logout(String token, LogoutRequest logoutRequest) {
        Optional<UserRefreshToken> refreshToken = this.refreshTokenService.findByToken(logoutRequest.getRefreshToken());
        refreshToken.ifPresent(this.refreshTokenService::deleteToken);
        this.tokenBlacklistService.blacklistToken(token);
    }


    private LoginResponseDto createLoginResponse(AdminUser user, String token) {
        return AuthUtils.createLoginResponse(user,token);
    }


    private boolean isEmptyToken(String token) {
        return AuthUtils.isEmptyToken(token);
    }

    private void createLoginHistory(AdminUser user, String prsRslt, String ip) {
        AdminLoginHistory loginHistory = new AdminLoginHistory();
        loginHistory.setUser(user);
        loginHistory.setPrcRslt(prsRslt);
        loginHistory.setIpAddr(ip);
        this.loginHistoryService.saveLoginHistory(loginHistory);
    }


}
