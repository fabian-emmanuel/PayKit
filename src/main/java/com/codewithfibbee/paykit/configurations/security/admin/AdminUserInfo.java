package com.codewithfibbee.paykit.configurations.security.admin;


import com.kingsaffiliate.app.constants.Constants;
import com.kingsaffiliate.app.models.users.UserStatus;
import com.kingsaffiliate.app.models.users.admin.AdminUser;
import com.kingsaffiliate.app.models.users.roles.Role;
import com.kingsaffiliate.app.security.BaseUserInfo;
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

    public Long getUserId() {
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