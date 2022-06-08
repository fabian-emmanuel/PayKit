package com.codewithfibbee.paykit.services.user.admin;

import com.codewithfibbee.paykit.models.users.roles.Role;
import com.codewithfibbee.paykit.payloads.user.role.ListRoleDto;
import com.codewithfibbee.paykit.payloads.user.role.RoleDetailDto;
import com.codewithfibbee.paykit.payloads.user.role.RoleRequestDto;

import java.util.Collection;
import java.util.Optional;


public interface RoleService  {
    Optional<Role> fetchByRoleKey(String roleKey);

    Role createRole(RoleRequestDto roleRequestDto);

    Collection<ListRoleDto> manageRoles();

    Collection<ListRoleDto> listRoles();

    Collection<ListRoleDto> listRolesForClient();

    Collection<ListRoleDto> listRolesForAdmin();

    RoleDetailDto fetchRoleDetail(String roleId);

    Role updateRole(String roleId, RoleRequestDto roleRequestDto);

    void deleteRole(String roleId);

    Collection<Role> getRoleByIds(Collection<String> ids);

    Collection<ListRoleDto> listRoles(String roleType);
}
