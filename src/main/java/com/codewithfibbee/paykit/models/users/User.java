package com.codewithfibbee.paykit.models.users;


import com.codewithfibbee.paykit.models.common.audit.AuditSection;
import com.codewithfibbee.paykit.models.common.audit.Auditable;
import com.codewithfibbee.paykit.models.common.generics.BaseEntity;
import com.codewithfibbee.paykit.enumtypes.UserStatus;
import com.codewithfibbee.paykit.enumtypes.UserType;
import com.codewithfibbee.paykit.utils.CommonUtils;
import com.codewithfibbee.paykit.utils.CustomDateUtils;
import lombok.*;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;
import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.codewithfibbee.paykit.constants.SchemaConstant.DEFAULT_PWRD_SETTING_VLDTY_TRM;


@EntityListeners(AuditListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Where(clause = "deleted='0'")
public abstract class User extends BaseEntity<String, User> implements Auditable {
    @Id
    private String _id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Email(message = "please enter a valid email value")
    @NotNull
    private String email;

    private String password;

    @Field(name = "password_reset_token")
    private String passwordResetToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(name = "password_reset_validity_term")
    private Date passwordResetValidityTerm;

    @Field(name = "last_login")
    private Date lastLogin;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(name = "status_date")
    private Date statusDate;

    @Field(name = "phone_number")
    private String phoneNumber;

    @Field(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Field(name = "profile_pic")
    private String profilePic;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    public boolean tokenExpired() {
        return this.passwordResetValidityTerm != null && this.passwordResetValidityTerm.before(CustomDateUtils.now());
    }

    public void calculateTokenExpiryDate(String valdtyTrm){
        if (!"0" .equals(valdtyTrm)) { //0 means no validity term used
            if (!CommonUtils.isInteger(valdtyTrm)) {
                this.setDefaultPasswordValidityTerm();
            } else {
                if (Integer.parseInt(valdtyTrm) < 0) {
                    this.setDefaultPasswordValidityTerm();
                } else {
                    this.setPasswordResetValidityTerm(DateUtils.addHours(CustomDateUtils.now(), Integer.parseInt(valdtyTrm)));
                }
            }
        }
    }

    private void setDefaultPasswordValidityTerm() {
        this.setPasswordResetValidityTerm(DateUtils.addHours(CustomDateUtils.now(), DEFAULT_PWRD_SETTING_VLDTY_TRM));
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        User user = (User) o;
//        return id != null && Objects.equals(id, user.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
