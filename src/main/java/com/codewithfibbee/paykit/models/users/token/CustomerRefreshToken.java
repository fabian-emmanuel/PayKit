package com.codewithfibbee.paykit.models.users.token;

import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.EntityListeners;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_CUSTOMER_REFRESH_TOKEN;

@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditListener.class)
@Document(collection = TABLE_CUSTOMER_REFRESH_TOKEN)
public class CustomerRefreshToken extends UserRefreshToken {

}
