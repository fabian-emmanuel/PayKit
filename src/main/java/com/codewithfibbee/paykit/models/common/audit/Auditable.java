package com.codewithfibbee.paykit.models.common.audit;

public interface Auditable {
    AuditSection getAuditSection();
    void setAuditSection(AuditSection auditSection);
}
