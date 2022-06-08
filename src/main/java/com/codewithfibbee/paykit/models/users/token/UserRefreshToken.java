package com.codewithfibbee.paykit.models.users.token;


import com.codewithfibbee.paykit.models.common.audit.AuditListener;
import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import com.codewithfibbee.paykit.utils.CommonUtils;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.codewithfibbee.paykit.utils.CustomDateUtils.now;

@Data
@EntityListeners(AuditListener.class)
public class UserRefreshToken implements Auditable {

    public static final int EXPIRATION = 60 * 24;

    @Id
    private String id;

    @NotNull
    private String token;

    @Field("validity_term")
    private Date validityTrm;

    @NotNull
    @Field("user_name")
    private String userName;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    public void calculateExpiryDate(String valdtyTrm) {

        if (!"0".equals(valdtyTrm)) { //0 means no validity term used
            if (!CommonUtils.isInteger(valdtyTrm)) {
                this.setDefaultValidityTerm();
            } else {
                if (Integer.parseInt(valdtyTrm) < 0) {
                    this.setDefaultValidityTerm();
                } else {
                    setValidityTrm(DateUtils.addMinutes(now(), Integer.parseInt(valdtyTrm)));
                }
            }
        }

    }

    private void setDefaultValidityTerm() {
        setValidityTrm(DateUtils.addMinutes(now(), EXPIRATION));
    }

    public boolean isExpired() {
        return this.validityTrm.before(now());
    }
}
