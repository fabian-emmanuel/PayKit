package com.codewithfibbee.paykit.payloads.user.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
@Data
public class AdminUserDetailResponseDto  {
    @JsonProperty(value = "user")
    private AdminUserDetailDto adminUserDto;
    private Map<String,Object> extras;
}
