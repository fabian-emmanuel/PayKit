package com.codewithfibbee.paykit.repositories.user;

import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AdminUserRepository extends MongoRepository<AdminUser,Long> {

    Optional<AdminUser> findByEmail(String email);

    @Query("select u from AdminUser u " +
            "left join fetch u.roles r " +
            "left join fetch r.permissions p  " +
            "where u.email =?1 " +
            "and u.auditSection.delF='0'")
    Optional<AdminUser> findAuthUserByEmail( String username);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long userId);

    Optional<AdminUser> findUserByPasswordResetToken(String token);
}
