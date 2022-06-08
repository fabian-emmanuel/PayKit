package com.codewithfibbee.paykit.exceptions;

public class TemplateEngineException extends RuntimeException {

    public TemplateEngineException(String message) {
        super(message);
    }

    public TemplateEngineException(String message, Throwable e) {
        super(message,e);
    }
}
