package com.codewithfibbee.paykit.models.users.admin;

import com.codewithfibbee.paykit.models.users.User;
import com.codewithfibbee.paykit.models.users.roles.Role;
import lombok.*;
import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_ADMIN_USER;
import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_ADMIN_USER_ROLE;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditListener.class)
@Document(collection = TABLE_ADMIN_USER)
public class AdminUser extends User {
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = TABLE_ADMIN_USER_ROLE,
            joinColumns = @JoinColumn(name = "admin_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.addAdminUser(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.removeAdminUser(this);
    }
}
