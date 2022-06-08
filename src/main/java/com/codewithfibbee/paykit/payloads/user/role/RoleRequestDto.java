package com.codewithfibbee.paykit.payloads.user.role;

import com.codewithfibbee.paykit.enumtypes.RoleType;
import com.codewithfibbee.paykit.validators.enumvalidator.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private Collection<String> permissions;
    @Enum(enumClass= RoleType.class)
    private String roleType;
    @NotEmpty
    @Size(max=100)
    private String name;
    @Size(max=255)
    private String description;

}
