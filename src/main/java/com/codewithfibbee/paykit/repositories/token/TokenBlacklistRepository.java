package com.codewithfibbee.paykit.repositories.token;

import com.codewithfibbee.paykit.models.users.token.TokenBlacklist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenBlacklistRepository extends MongoRepository<TokenBlacklist, Long> {

     boolean existsByToken(String token);
}
