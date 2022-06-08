package com.codewithfibbee.paykit.payloads.user.admin;

import com.codewithfibbee.paykit.enumtypes.UserStatus;
import com.codewithfibbee.paykit.payloads.user.ListUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ListAdminUserDto extends ListUserDto {

    public ListAdminUserDto(String id, String name, String email, String phone, UserStatus status, Date dateAdded) {
        super(id, name,email,phone,status,dateAdded);
    }
}



