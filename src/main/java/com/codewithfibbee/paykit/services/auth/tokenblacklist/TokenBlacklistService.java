package com.codewithfibbee.paykit.services.auth.tokenblacklist;

public interface TokenBlacklistService {
     void blacklistToken(String token);

    void purgeExpiredTokens();

    boolean isBlacklisted(String token);
}
