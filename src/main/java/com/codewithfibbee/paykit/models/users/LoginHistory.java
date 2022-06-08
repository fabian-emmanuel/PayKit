package com.codewithfibbee.paykit.models.users;


import com.codewithfibbee.paykit.constants.PrcRsltCode;
import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;

@EntityListeners(value = AuditListener.class)
@Data
public class LoginHistory implements Auditable {

    @Id
    protected String id;

    @Field("IP_ADDR")
    private String ipAddr;

    @Field("PRC_RSLT")
    private String prcRslt= PrcRsltCode.FAILURE; //failure by default

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
