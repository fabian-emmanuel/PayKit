package com.codewithfibbee.paykit.payloads.system;

import com.codewithfibbee.paykit.models.common.ItemListingDto;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Data
public class ConfigurationMetadata {
    Collection<ItemListingDto> list= Collections.emptyList();
}
