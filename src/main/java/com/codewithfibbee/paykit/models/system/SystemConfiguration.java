package com.codewithfibbee.paykit.models.system;


import com.codewithfibbee.paykit.enumtypes.SystemConfigrationType;
import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;

import static com.codewithfibbee.paykit.constants.SchemaConstant.TABLE_SYSTEM_CONFIGURATION;


@Data
@EntityListeners(AuditListener.class)
@Document(collection = TABLE_SYSTEM_CONFIGURATION)
public class SystemConfiguration implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Field("configuration_name")
    private String configurationName;

    private String value;

    @Field("configuration_key")
    private String configurationKey;

    private String description;

    @Field("configuration_group")
    private String configurationGroup;

    @Field("sort_order")
    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Field("system_configuration_type")
    private SystemConfigrationType systemConfigrationType=SystemConfigrationType.TEXT;

    @Embedded
    private AuditSection auditSection = new AuditSection();
}
