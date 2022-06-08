package com.codewithfibbee.paykit.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CustomSystemException extends RuntimeException{

    protected String message;

    public CustomSystemException(String message) {
        this.message=message;
    }

    public CustomSystemException(Throwable cause) {
        super(cause);
    }

    public CustomSystemException(String message, Throwable cause) {
        super(message, cause);
        this.message=message;
    }
}
