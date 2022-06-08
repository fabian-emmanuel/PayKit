package com.codewithfibbee.paykit.exceptions;


import com.codewithfibbee.paykit.enumtypes.EntityType;
import com.codewithfibbee.paykit.enumtypes.ExceptionType;
import com.kingsaffiliate.app.utils.ErrorMsgUtils;

public class ResourceNotFoundException extends RuntimeException {

  private static final String ERROR_CODE = "404";
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String errorCode, String message) {
        super(errorCode);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(EntityType entityType, String id) {
      super(ErrorMsgUtils.formatMsg(entityType.name(), ExceptionType.ENTITY_NOT_FOUND.getValue(),id));
    }
}
