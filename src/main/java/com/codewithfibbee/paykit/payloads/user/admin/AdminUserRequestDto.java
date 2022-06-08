package com.codewithfibbee.paykit.payloads.user.admin;

import com.codewithfibbee.paykit.payloads.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserRequestDto extends UserDto {
    Collection<String> roles;
}
