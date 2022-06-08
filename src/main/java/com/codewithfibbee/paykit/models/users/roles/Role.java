package com.codewithfibbee.paykit.models.users.roles;


import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import com.codewithfibbee.paykit.enumtypes.RoleType;
import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import com.codewithfibbee.paykit.models.users.permissions.Permission;
import lombok.*;
//import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.Where;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_ROLE;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditListener.class)
@Document(collection= TABLE_ROLE)
public class Role implements Auditable {
    @Id
    private String id;

    private String name;
    private String description;

    @Field("role_key")
    private String roleKey;

    @Enumerated(EnumType.STRING)
    @Field("role_type")
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<AdminUser> adminUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Set<Permission> permissions = new HashSet<>();

    @Embedded
    private AuditSection auditSection = new AuditSection();

    private boolean systemCreated;

    public void addPermission(Permission permission) {
        permission.addRole(this);
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        permission.removeRole(this);
        permissions.remove(permission);
    }
   
    public void addAdminUser(AdminUser adminUser){
        this.adminUsers.add(adminUser);
    }

    public void removeAdminUser(AdminUser adminUser){
        this.adminUsers.remove(adminUser);
    }
    
    
//    @Override
//    public String toString() {
//        return "Role{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", roleKey='" + roleKey + '\'' +
//                ", roleType=" + roleType +
//                ", auditSection=" + auditSection +
//                '}';
//    }
}
