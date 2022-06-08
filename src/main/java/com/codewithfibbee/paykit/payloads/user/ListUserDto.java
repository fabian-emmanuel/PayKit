package com.codewithfibbee.paykit.payloads.user;

import com.codewithfibbee.paykit.enumtypes.UserStatus;
import com.codewithfibbee.paykit.payloads.common.ListingDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.codewithfibbee.paykit.constants.DateDisplayConstants.DATE_TIME_DISPLAY_FORMAT;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ListUserDto extends ListingDto {

    private String status;
    @JsonFormat(pattern = DATE_TIME_DISPLAY_FORMAT)
    private Date dateAdded;
    private String email;
    private String phone;

    public ListUserDto(String id, String name, String email, String phone, UserStatus status, Date dateAdded) {
        super(id, name);
        this.status = status.name();
        this.dateAdded = dateAdded;
        this.email=email;
        this.phone=phone;
    }
}



