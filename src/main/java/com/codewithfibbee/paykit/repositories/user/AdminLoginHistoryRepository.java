package com.codewithfibbee.paykit.repositories.user;


import com.codewithfibbee.paykit.models.users.admin.AdminLoginHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminLoginHistoryRepository extends MongoRepository<AdminLoginHistory, Long> {
}
