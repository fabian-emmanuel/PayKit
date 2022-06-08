package com.codewithfibbee.paykit.configurations.security.admin;


import com.kingsaffiliate.app.exceptions.CustomException;
import com.kingsaffiliate.app.models.users.UserStatus;
import com.kingsaffiliate.app.models.users.admin.AdminUser;
import com.kingsaffiliate.app.models.users.roles.Role;
import com.kingsaffiliate.app.repositories.user.AdminUserRepository;
import com.kingsaffiliate.app.services.user.admin.AdminUserService;
import com.kingsaffiliate.app.services.user.role.RoleService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.kingsaffiliate.app.constants.Constants.*;
import static com.kingsaffiliate.app.constants.SchemaConstant.DEFAULT_SUPER_ADMINISTRATOR_DEFAULT_PASS;
import static com.kingsaffiliate.app.constants.SchemaConstant.DEFAULT_SUPER_ADMINISTRATOR_EMAIL;


/**
 *
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Component("adminUserDetailsService")
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminUserRepository userRepository;
    private final AdminUserService adminUserService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    @Cacheable("adminUserAuthInfo")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user = userRepository.findAuthUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username:" + username));
        return new AdminUserInfo(user);
    }

    public void createDefaultAdmin() throws ServiceException {
        if (this.adminUserService.findUserByEmail(DEFAULT_SUPER_ADMINISTRATOR_EMAIL).isEmpty()) {
            AdminUser user = new AdminUser();
            String password = passwordEncoder.encode(DEFAULT_SUPER_ADMINISTRATOR_DEFAULT_PASS);
            Role role = roleService.fetchByRoleKey(ROLE_SUPER_ADMIN).orElseThrow(() -> new CustomException.EntityNotFoundException("Resource not found for role with key:" + ROLE_SUPER_ADMIN));
            //creation of the super admin admin:password)
            user.setFirstName("Administrator");
            user.setLastName("SuperAdmin");
            user.setEmail(DEFAULT_SUPER_ADMINISTRATOR_EMAIL);
            user.setPassword(password);
            user.addRole(role);
            user.setStatus(UserStatus.ACTIVE);
            adminUserService.create(user);
        }
    }
}
