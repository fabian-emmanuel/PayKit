package com.codewithfibbee.paykit.validators;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ValidationErrors {

    private List<FieldError> fieldErrors;
    private static ValidationErrors instance;

    /*private ValidationErrors(){
        fieldErrors=new ArrayList<>();
    }*/

    public ValidationErrors(){
        fieldErrors=new ArrayList<>();
    }

    static {
        instance=null;
    }

    public static ValidationErrors getInstance(){
        if(instance == null){
            instance = new ValidationErrors();
        }
        return instance;
    }

    public void addError(String objectName, String field, Object rejectedValue, String defaultMessage){
        String[] codes={};
        Object[] arguments={};
        FieldError fieldError=new FieldError(objectName,field,rejectedValue,true,codes,arguments,defaultMessage);
        fieldErrors.add(fieldError);
    }

    public List<FieldError> getFieldErrors(){
        return this.fieldErrors;
    }

    public boolean hasErrors() {
        return isNotEmpty(this.fieldErrors);
    }
}
