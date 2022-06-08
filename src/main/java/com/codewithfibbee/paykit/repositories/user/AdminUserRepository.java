package com.codewithfibbee.paykit.repositories.user;

import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import com.codewithfibbee.paykit.payloads.user.admin.ListAdminUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AdminUserRepository extends MongoRepository<AdminUser,String> {

    Optional<AdminUser> findByEmail(String email);

    @Query("select u from AdminUser u " +
            "left join fetch u.roles r " +
            "left join fetch r.permissions p  " +
            "where u.email =?1 " +
            "and u.auditSection.delF='0'")
    Optional<AdminUser> findAuthUserByEmail( String username);


    @Query("select new com.paykit.dto.user.admin.ListAdminUserDto(u.id,CONCAT(u.lastName,' ', u.firstName),u.email,u.telephoneNumber,u.status,u.auditSection.dateCreated) from AdminUser u WHERE  u.auditSection.delF <> '1' order by u.lastName asc")
    Page<ListAdminUserDto> listAdminUsers(Pageable pageable);

    @Query("select u from AdminUser u left join fetch u.roles r where u.id =?1 and u.auditSection.delF='0'")
    Optional<AdminUser> findAdminUserDetail(final String userId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String userId);

    Optional<AdminUser> findUserByPasswordResetToken(String token);
}
