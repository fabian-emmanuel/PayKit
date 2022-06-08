package com.codewithfibbee.paykit.enumtypes;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    DUPLICATE_ENTITY("duplicate"),
    UNAUTHORIZED("unauthorized"),
    ENTITY_EXCEPTION("exception");

    final String value;

    ExceptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
