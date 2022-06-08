package com.codewithfibbee.paykit.services.user.admin;


import com.codewithfibbee.paykit.enumtypes.PermissionType;
import com.codewithfibbee.paykit.models.users.permissions.Permission;
import com.codewithfibbee.paykit.payloads.user.permission.PermissionDto;

import java.util.Collection;
import java.util.Optional;

public interface PermissionService   {
    Optional<Permission> findPermByKey(String anyString);
    Collection<Permission> fetchPermissions();
    Optional<Permission> fetchByPermission(String permission);

    Collection<Permission> findByPermissionIn(Collection<String> names);

    Collection<Permission> fetchPermissions(PermissionType permissionType);

    Collection<PermissionDto> listPermissions();
}
