package com.codewithfibbee.paykit.payloads.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserProfileDto extends UserDto{
    private String userId;
    private String profilePic;
}
