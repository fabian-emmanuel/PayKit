package com.codewithfibbee.paykit.services.user.admin;

import com.codewithfibbee.paykit.constants.Constants;
import com.codewithfibbee.paykit.enumtypes.PermissionType;
import com.codewithfibbee.paykit.enumtypes.RoleType;
import com.codewithfibbee.paykit.exceptions.InvalidOperationException;
import com.codewithfibbee.paykit.exceptions.ResourceNotFoundException;
import com.codewithfibbee.paykit.models.users.permissions.Permission;
import com.codewithfibbee.paykit.models.users.roles.Role;
import com.codewithfibbee.paykit.payloads.user.role.ListRoleDto;
import com.codewithfibbee.paykit.payloads.user.role.RoleDetailDto;
import com.codewithfibbee.paykit.payloads.user.role.RoleRequestDto;
import com.codewithfibbee.paykit.repositories.role.RoleRepository;
import com.codewithfibbee.paykit.utils.UserInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codewithfibbee.paykit.enumtypes.EntityType.ROLE;


@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final PermissionService permissionService;
    private final UserInfoUtil userInfoUtil;

//    @Autowired
//    public RoleServiceImpl(RoleRepository repository, PermissionService permissionService,
//                           UserInfoUtil userInfoUtil) {
//        super(repository);
//        this.repository = repository;
//        this.permissionService = permissionService;
//        this.userInfoUtil = userInfoUtil;
//    }

    @Override
    public Optional<Role> fetchByRoleKey(String roleKey) {
        return this.repository.findByRoleKey(roleKey);
    }

    @Override
    @Transactional
    public Role createRole(RoleRequestDto roleDto) {
        Role role = new Role();
        this.mapDtoToEntityModel(roleDto, role);
        //role.setRoleType(RoleType.ADMIN);
        Collection<Permission> permissions = this.permissionService.findByPermissionIn(roleDto.getPermissions());
        permissions.forEach((role::addPermission));
        return this.saveRole(role);
    }

    @Override
    public Collection<ListRoleDto> manageRoles() {
        return this.repository.listRoles();
    }


   @Override
    public Collection<ListRoleDto> listRoles() {
        if (userInfoUtil.isAdmin()) {
            return this.listRolesForAdmin();
        }
//        else if () {
//            return this.listRolesForClient();
//        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ListRoleDto> listRolesForClient() {
       return this.repository.listRolesForClient();
    }

    @Override
    public Collection<ListRoleDto> listRolesForAdmin() {
        return this.repository.listRolesForAdmin();
    }


    @Override
    public RoleDetailDto fetchRoleDetail(String roleId) {
        Optional<Role> role = this.repository.findRoleDetailById(roleId);
        if (role.isEmpty()) {
            throw new ResourceNotFoundException(ROLE, String.valueOf(roleId));
        }
        //only admin type role allowed to be fetched in this method
        /*if (!isAdminRole(role.get())) {
            throw new UnAuthorizeEntityAccessException(ROLE);
        }*/
        return this.convertToDetailDto(role.get(), PermissionType.ADMIN);
    }

    @Override
    @Transactional
    @CacheEvict(value="adminUserAuthInfo", allEntries = true)
    public Role updateRole(String roleId, RoleRequestDto roleRequestDto) {
        Optional<Role> role = this.repository.findRoleDetailById(roleId);
        if (role.isEmpty()) {
            throw new ResourceNotFoundException(ROLE, String.valueOf(roleId));
        }

        //only admin type role allowed to be updated in this method
       /* if (!isAdminRole(role.get())) {
            throw new UnAuthorizeEntityAccessException(ROLE, String.valueOf(roleId));
        }*/

        this.mapDtoToEntityModel(roleRequestDto, role.get());
        Collection<Permission> permissions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleRequestDto.getPermissions())) {
            permissions = this.permissionService.findByPermissionIn(roleRequestDto.getPermissions());
        }
        this.updateRolePermission(role.get(), permissions);
        return this.updateRole(role.get());
    }

    @Override
    @CacheEvict(value="adminUserAuthInfo", allEntries = true)
    public void deleteRole(String roleId) {
        Optional<Role> optional = this.repository.findById(roleId);
        //only admin type role allowed to be updated in this method
        optional.ifPresent(role -> {
            if (role.isSystemCreated()) {
                throw new InvalidOperationException();
            }
            this.repository.delete(role);
        });
    }

    @Override
    public Collection<Role> getRoleByIds(Collection<String> ids) {
        try {
            return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : (Collection<Role>) repository.findAllById(ids);
        } catch (Exception e) {
            //throw new ServiceException(e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ListRoleDto> listRoles(String roleType) {
//        if(Constants.ROLE_TYPE_CLIENT.equals(roleType)){
//           return this.listRolesForClient();
//        }else
            if(Constants.ROLE_TYPE_ADMIN.equals(roleType)){
            return this.listRolesForAdmin();
        }
        return Collections.emptyList();
    }

    private void updateRolePermission(Role role, Collection<Permission> permissions) {

        Collection<Permission> definedPermissions = role.getPermissions();
        Collection<String> userRequestPermissions = permissions.stream().map(Permission::getPermission).toList();
        Collection<Permission> uncheckedList = definedPermissions.stream().filter(perm -> !userRequestPermissions.contains(perm.getPermission())).collect(Collectors.toList());

        permissions.forEach(role::addPermission);
        uncheckedList.forEach(role::removePermission);

    }

    private Role updateRole(Role role) {
        return role;
    }

    private boolean isAdminRole(Role role) {
        return role.getRoleType().equals(RoleType.ADMIN);
    }

    private RoleDetailDto convertToDetailDto(Role role, PermissionType permissionType) {

        RoleDetailDto roleDetailDto = new RoleDetailDto();
        roleDetailDto.setId(role.getId());
        roleDetailDto.setName(role.getName());
        roleDetailDto.setDescription(role.getDescription());

        Collection<Permission> rolePermissions = role.getPermissions();

        //Collection<Permission> allPermissions = permissionService.fetchPermissions(permissionType);
        Collection<Permission> allPermissions = permissionService.fetchPermissions();
        Collection<String> rolePerms = new ArrayList<>();
        if (rolePermissions != null && !rolePermissions.isEmpty()) {
            rolePerms = rolePermissions.stream().map(Permission::getPermission).collect(Collectors.toList());
        }
        boolean checked;
        String permission;
        for (Permission perm : allPermissions) {
            permission = perm.getPermission();
            if (!rolePerms.isEmpty() && rolePerms.contains(permission)) {
                checked = true;
            } else {
                checked = false;
            }
            roleDetailDto.addPermission(roleDetailDto.new PermissionDTO(permission, perm.getName(), checked));
        }
        return roleDetailDto;
    }

    private Role saveRole(Role role) {
        return this.repository.save(role);
    }

    private void mapDtoToEntityModel(RoleRequestDto roleDto, Role role) {
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setRoleType(RoleType.valueOf(roleDto.getRoleType()));
    }
}
