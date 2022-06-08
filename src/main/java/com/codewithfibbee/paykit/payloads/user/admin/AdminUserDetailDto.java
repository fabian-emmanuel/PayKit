package com.codewithfibbee.paykit.payloads.user.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserDetailDto extends AdminUserRequestDto{
    private String userId;
}
