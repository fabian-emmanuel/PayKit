package com.codewithfibbee.paykit.configurations.security;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseUserInfo implements UserDetails {

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
