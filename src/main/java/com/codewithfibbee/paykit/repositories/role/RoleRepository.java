package com.codewithfibbee.paykit.repositories.role;



import com.codewithfibbee.paykit.models.users.roles.Role;
import com.codewithfibbee.paykit.payloads.user.role.ListRoleDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByRoleKey(String key);

    //@Query("select new com.decagon.fellowship.dtos.user.role.ListRoleDto(r.id,r.name,r.description) from Role r WHERE r.roleType=com.decagon.fellowship.models.user.RoleType.ADMIN and r.auditSection.delF <> '1' order by r.name asc")
    @Query("select new com.paykit.payloads.user.role.ListRoleDto(r.id,r.name,r.description,r.roleType,r.systemCreated) from Role r WHERE r.auditSection.delF <> '1' order by r.name asc")
    Collection<ListRoleDto> listRoles();

    @Query("select r from Role r " +
            "left join fetch r.permissions p  " +
            "where r.id =:roleId ")
    Optional<Role> findRoleDetailById(String roleId);

    @Query("select new com.paykit.payloads.user.role.ListRoleDto(r.id,r.name,r.description,r.roleType,r.systemCreated) " +
            "from Role r " +
            "WHERE r.roleType=com.paykit.models.users.roles.RoleType.CLIENT " +
            "or r.roleType=com.indulgetech.models.users.roles.RoleType.GLOBAL " +
            "and r.auditSection.delF <> '1' " +
            "order by r.name asc")
    Collection<ListRoleDto> listRolesForClient();

    @Query("select new com.paykit.payloads.user.role.ListRoleDto(r.id,r.name,r.description,r.roleType,r.systemCreated) " +
            "from Role r " +
            "WHERE r.roleType=com.paykit.models.users.roles.RoleType.ADMIN " +
            "and r.auditSection.delF <> '1' " +
            "order by r.name asc")
    Collection<ListRoleDto> listRolesForAdmin();

}
