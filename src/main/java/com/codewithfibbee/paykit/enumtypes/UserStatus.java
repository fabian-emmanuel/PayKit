package com.codewithfibbee.paykit.enumtypes;


import com.codewithfibbee.paykit.models.common.ItemListingDto;

import java.util.ArrayList;
import java.util.Collection;

public enum UserStatus {

    ACTIVE, INACTIVE;

    public static Collection<ItemListingDto> toItemList() {
        Collection<ItemListingDto> list = new ArrayList<>();
        for (UserStatus userStatus : UserStatus.values()) {
            list.add(new ItemListingDto(userStatus.name(), userStatus.name()));
        }
        return list;
    }
}
