package com.codewithfibbee.paykit.configurations.security.admin;


import com.codewithfibbee.paykit.configurations.security.BaseUserInfo;
import com.codewithfibbee.paykit.constants.Constants;
import com.codewithfibbee.paykit.enumtypes.UserStatus;
import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import com.codewithfibbee.paykit.models.users.roles.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
public class AdminUserInfo extends BaseUserInfo {

    private final AdminUser user;

    public AdminUserInfo(AdminUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getUserAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getUserId() {
        return user.getId();
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(UserStatus.ACTIVE);
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities() {
        return getGrantedAuthorities(getPrivileges(this.user.getRoles()));
    }

    private Collection<String> getPrivileges(Collection<Role> roles) {
        Collection<String> privileges = new HashSet<>();
        roles.forEach((role) -> {
            privileges.add(Constants.ROLE_PREFIX +role.getRoleKey());
            role.getPermissions().forEach(perm -> privileges.add(perm.getPermission()));
        });
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        privileges.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege)));
        return authorities;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userName=" + this.getUsername() +
                ",isEnabled=" + this.isEnabled() +
                "user=" + user +
                '}';
    }
}