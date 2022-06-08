package com.codewithfibbee.paykit.services.user.admin;

import com.codewithfibbee.paykit.enumtypes.PermissionType;
import com.codewithfibbee.paykit.models.users.permissions.Permission;
import com.codewithfibbee.paykit.payloads.user.permission.PermissionDto;
import com.codewithfibbee.paykit.repositories.permission.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private  final PermissionRepository repository;

//    @Autowired
//    public PermissionServiceImpl(PermissionRepository repository) {
//        super(repository);
//        this.repository = repository;
//    }

    @Cacheable
    @Override
    public Optional<Permission> findPermByKey(String permKey) {
        return this.repository.findByPermission(permKey);
    }

    @Override
    public Collection<Permission> fetchPermissions() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Permission> fetchByPermission(String permission) {
        return this.repository.findByPermission(permission);
    }

    @Override
    public Collection<Permission> findByPermissionIn(Collection<String> names) {
        return CollectionUtils.isEmpty(names) ? Collections.emptyList() : repository.findByPermissionIn(names);
    }

    @Override
    public Collection<Permission> fetchPermissions(PermissionType permissionType) {
        return this.repository.findAllByPermissionType(permissionType);
    }

    @Override
    public Collection<PermissionDto> listPermissions() {
        Collection<Permission> permissions=this.fetchPermissions();
        return !CollectionUtils.isEmpty(permissions) ? this.convertEntityToDtos(permissions) : Collections.emptyList();
    }

    private Collection<PermissionDto> convertEntityToDtos(Collection<Permission> permissions) {
        return permissions.stream().map((p)->new PermissionDto(p.getPermission(),p.getName(),p.getDescription())).collect(Collectors.toList());
    }


}
