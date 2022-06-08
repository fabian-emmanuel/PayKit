package com.codewithfibbee.paykit.payloads.user;

import com.codewithfibbee.paykit.enumtypes.UserStatus;
import com.codewithfibbee.paykit.validators.enumvalidator.Enum;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude()
public class UserDto {

    @NotEmpty
    @Size(max = 100)
    protected String firstName;
    @NotEmpty
    @Size(max = 100)
    protected String lastName;
    @NotEmpty
    @Email
    @Size(max = 100)
    protected String email;
    @NotEmpty
    @Size(max = 100)
    @JsonAlias({ "phone" })
    protected String phone;
    @Enum(enumClass= UserStatus.class)
    protected String status= UserStatus.ACTIVE.name();
}
