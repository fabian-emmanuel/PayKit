package com.codewithfibbee.paykit.models.common.generics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
//import org.hibernate.Hibernate;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

@Data
public abstract class BaseEntity<K , E extends BaseEntity<K, ?>> {

//    @Serial
//    private static final long serialVersionUID = -3988499137919577054L;

    public static final Collator DEFAULT_STRING_COLLATOR = Collator.getInstance(Locale.FRENCH);

    static {
        DEFAULT_STRING_COLLATOR.setStrength(Collator.PRIMARY);
    }

    /**
     * Sets the value of the unique identifier.
     *
     */
    public abstract K get_id();

    /**
     * Sets the value of the unique identifier.
     *
     * @param id id
     */
    public abstract void set_id(K id);

    /**
     * Indicates whether the object has already been persisted or not
     *
     * @return true if the object has not yet been persisted
     */
    @JsonIgnore
    public boolean isNew() {
        return get_id() == null;
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public boolean equals(Object object) {
//        if (object == null) {
//            return false;
//        }
//        if (object == this) {
//            return true;
//        }
//
//        if (Hibernate.getClass(object) != Hibernate.getClass(this)) {
//            return false;
//        }
//
//        BaseEntity<K, E> entity = (BaseEntity<K, E>) object; //  treated above but Hibernate wrapper
//        K id = get_id();
//
//        if (id == null) {
//            return false;
//        }
//
//        return id.equals(entity.get_id());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//
//    public int compareTo(E o) {
//        if (this == o) {
//            return 0;
//        }
//        return this.get_id().compareTo(o.get_id());
//    }
//
//    @Override
//    public String toString() {
//        return "entity." +
//                Hibernate.getClass(this).getSimpleName() +
//                "<" +
//                get_id() +
//                "-" +
//                super.toString() +
//                ">";
//    }
}
