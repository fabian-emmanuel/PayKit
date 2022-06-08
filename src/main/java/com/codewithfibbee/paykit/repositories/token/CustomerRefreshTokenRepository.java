package com.codewithfibbee.paykit.repositories.token;

import com.codewithfibbee.paykit.models.users.token.CustomerRefreshToken;
import com.codewithfibbee.paykit.models.users.token.UserRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRefreshTokenRepository extends MongoRepository<CustomerRefreshToken, Long> {
    Optional<UserRefreshToken> findByToken(String token);
}
