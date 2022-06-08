package com.codewithfibbee.paykit.services.user.admin;


import com.codewithfibbee.paykit.models.users.admin.AdminUser;
import com.codewithfibbee.paykit.payloads.auth.ResetPasswordDto;
import com.codewithfibbee.paykit.payloads.user.ChangePasswordDto;
import com.codewithfibbee.paykit.payloads.user.UpdateUserProfileDto;
import com.codewithfibbee.paykit.payloads.user.UserProfileDto;
import com.codewithfibbee.paykit.payloads.user.admin.AdminUserDetailDto;
import com.codewithfibbee.paykit.payloads.user.admin.AdminUserDetailResponseDto;
import com.codewithfibbee.paykit.payloads.user.admin.AdminUserRequestDto;
import com.codewithfibbee.paykit.payloads.user.admin.ListAdminUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AdminUserService {

    AdminUser createUser(AdminUserRequestDto adminUserRequestDto);

    Page<ListAdminUserDto> listUsers(Pageable pageable);

    UserProfileDto fetchAdminUserProfile(String username);

    void changePassword(String user, ChangePasswordDto changePasswordDto);

    AdminUserDetailResponseDto fetchAdminUserDetail(final String userId);

    AdminUser updateAdminUser(String userId, AdminUserRequestDto dto);

    void deactivateUser(final String userId);

    void activateUser(final String userId);

    void create(AdminUser adminUser);

    void publishForgotPasswordEmail(String email);

    Optional<AdminUser> findUserByEmail(String email);

    void resetPassword(ResetPasswordDto resetPasswordDto);

    AdminUserDetailDto updateProfile(String userId, UpdateUserProfileDto dto);

    String updateProfilePic(String userId, MultipartFile profilePic);

}
