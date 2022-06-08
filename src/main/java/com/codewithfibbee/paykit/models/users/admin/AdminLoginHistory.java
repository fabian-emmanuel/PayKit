package com.codewithfibbee.paykit.models.users.admin;

import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.users.LoginHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_ADMIN_LOGIN_HISTORY;


@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = AuditListener.class)
@Document(collection = TABLE_ADMIN_LOGIN_HISTORY)
public class AdminLoginHistory extends LoginHistory {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private AdminUser user;
}
