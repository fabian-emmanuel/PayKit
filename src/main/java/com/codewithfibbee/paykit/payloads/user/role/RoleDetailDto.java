package com.codewithfibbee.paykit.payloads.user.role;

import com.codewithfibbee.paykit.payloads.user.permission.PermissionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class RoleDetailDto {

    private String id;
    private String name;
    private String description;

    @JsonProperty("permissions")
    private Collection<PermissionDTO> perms = new ArrayList<>();

    public void addPermission(PermissionDTO dto) {
        perms.add(dto);
    }

    @Setter
    @Getter
    public class PermissionDTO extends PermissionDto {
        private boolean checked;
        public PermissionDTO(String permission, String permissionKey, boolean checked) {
            super(permission, permissionKey);
            this.checked = checked;
        }
    }
}
