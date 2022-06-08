package com.codewithfibbee.paykit.models.common.audit;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
public class AuditSection implements Serializable {
    @CreatedDate
    @Field(name = "date_created")
    private Date dateCreated;

    @LastModifiedDate
    @Field(name = "date_modified")
    private Date dateModified;

    @Field(name = "modified_by")
    private String modifiedBy;

    @Field(name="deleted")
    private String delF="0";

//    public AuditSection() {}

//    public Date getDateCreated() {
//        return CloneUtils.clone(dateCreated);
//    }
//
//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = CloneUtils.clone(dateCreated);
//    }
//
//    public Date getDateModified() {
//        return CloneUtils.clone(dateModified);
//    }
//
//    public void setDateModified(Date dateModified) {
//        this.dateModified = CloneUtils.clone(dateModified);
//    }

//    public Long getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(Long modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
//
//    public String getDelF() {
//        return delF;
//    }
//
//    public void setDelF(String delF) {
//        this.delF = delF;
//    }

//    @Override
//    public String toString() {
//        return "AuditSection{" +
//                "dateCreated=" + dateCreated +
//                ", dateModified=" + dateModified +
//                ", modifiedBy='" + modifiedBy + '\'' +
//                ", delF='" + delF + '\'' +
//                '}';
//    }
}
