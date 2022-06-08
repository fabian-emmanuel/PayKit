package com.codewithfibbee.paykit.models.users.permissions;


import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import com.codewithfibbee.paykit.enumtypes.PermissionType;
import com.codewithfibbee.paykit.models.users.roles.Role;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_PERMISSION;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditListener.class)
@Document(collection= TABLE_PERMISSION)
public class Permission implements Auditable {
    @Id
    private String id;

    private String permission;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Field("permission_type")
    private PermissionType permissionType;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        roles.remove(role);
    }

    @Embedded
    private AuditSection auditSection = new AuditSection();

    public Permission(String permission) {
        this.permission=permission;
    }

//    @Override
//    public String toString() {
//        return "Permission{" +
//                "id=" + id +
//                ", permission='" + permission + '\'' +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", permissionType=" + permissionType +
//                '}';
//    }
}
