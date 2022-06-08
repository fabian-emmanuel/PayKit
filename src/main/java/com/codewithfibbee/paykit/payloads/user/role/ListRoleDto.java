package com.codewithfibbee.paykit.payloads.user.role;

import com.codewithfibbee.paykit.enumtypes.RoleType;
import com.codewithfibbee.paykit.payloads.common.ListingDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ListRoleDto extends ListingDto {

   private String description;
   private String roleType;
    @JsonProperty("isSystemCreated")
    private boolean isSystemCreated;

    public ListRoleDto(String id, String name, String description, RoleType roleType, boolean systemCreated) {
        super(id, name);
        this.description=description;
        this.roleType=roleType!=null?roleType.name():"";
        this.isSystemCreated=systemCreated;
    }

}
