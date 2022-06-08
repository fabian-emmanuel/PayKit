package com.codewithfibbee.paykit.models.users.token;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_TOKEN_BLACKLIST;

@Data
@Document(collection = TABLE_TOKEN_BLACKLIST)
public class TokenBlacklist {
    @Id
    private String id;
    private String token;
}