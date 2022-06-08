package com.codewithfibbee.paykit.utils;


import com.codewithfibbee.paykit.models.users.User;
import com.codewithfibbee.paykit.models.users.roles.Role;
import com.codewithfibbee.paykit.payloads.auth.UserDto;
import com.codewithfibbee.paykit.payloads.auth.response.LoginResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static com.codewithfibbee.paykit.constants.Constants.ROLE_PREFIX;

public class AuthUtils {

    public static Collection<String> buildAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public static LoginResponseDto createLoginResponse(User user, String token) {
        UserDto userDto;
        userDto=new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return new LoginResponseDto(userDto, token);
    }

    public static boolean isEmptyToken(String token) {
        return StringUtils.isEmpty(token);
    }

    public static  Collection<? extends GrantedAuthority> getUserAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private static Collection<String> getPrivileges(Collection<Role> roles) {
        Collection<String> privileges = new HashSet<>();
        roles.forEach(role -> {
            privileges.add(ROLE_PREFIX +role.getRoleKey());
            role.getPermissions().forEach(perm -> privileges.add(perm.getPermission()));
        });
        return privileges;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        privileges.forEach((privilege) -> authorities.add(new SimpleGrantedAuthority(privilege)));
        return authorities;
    }

    public static Collection<String> buildAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
