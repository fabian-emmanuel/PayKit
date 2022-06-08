package com.codewithfibbee.paykit.services.user;



import com.codewithfibbee.paykit.models.users.User;

import java.util.Optional;

public interface UserService{

     Optional<User> findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user,String token);

    String validatePasswordResetToken(String token);

    Optional<User> findUserByPasswordResetToken(String token);

    void resetPassword(User user, String password);

    String geUri();
}
