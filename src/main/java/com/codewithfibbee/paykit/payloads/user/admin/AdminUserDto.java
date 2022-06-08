package com.codewithfibbee.paykit.payloads.user.admin;

import com.codewithfibbee.paykit.payloads.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserDto extends UserDto {

    @Size(min = 6,max=100)
    protected String password;
}
