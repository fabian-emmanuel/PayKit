package com.codewithfibbee.paykit.repositories.permission;


import com.codewithfibbee.paykit.enumtypes.PermissionType;
import com.codewithfibbee.paykit.models.users.permissions.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission,Integer> {
    Optional<Permission> findByPermission(String permKey);

    Collection<Permission> findByPermissionIn(Collection<String> names);

    Collection<Permission> findAllByPermissionType(PermissionType permissionType);
}
