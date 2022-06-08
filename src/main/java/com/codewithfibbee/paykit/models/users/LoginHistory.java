package com.codewithfibbee.paykit.models.users;


import com.codewithfibbee.paykit.constants.PrcRsltCode;
import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import com.codewithfibbee.paykit.models.common.generics.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;

//@MappedSuperclass
@EntityListeners(value = AuditListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginHistory extends BaseEntity<String, LoginHistory> implements Auditable {

    @Id
    protected String _id;

    @Column(name = "IP_ADDR")
    private String ipAddr;

    @Column(name = "PRC_RSLT", columnDefinition = "varchar(1) not null")
    private String prcRslt= PrcRsltCode.FAILURE; //failure by default

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
