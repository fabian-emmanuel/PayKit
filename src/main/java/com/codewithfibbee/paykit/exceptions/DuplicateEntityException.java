package com.codewithfibbee.paykit.exceptions;


import com.codewithfibbee.paykit.enumtypes.EntityType;
import com.codewithfibbee.paykit.enumtypes.ExceptionType;
import com.codewithfibbee.paykit.utils.ErrorMsgUtils;

public class DuplicateEntityException extends RuntimeException {

  private static final String ERROR_CODE = "404";
    /**
   *
   */
//  private static final long serialVersionUID = 1L;

    public DuplicateEntityException(String errorCode, String message) {
        super(errorCode);
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(EntityType entityType, String id) {
      super(ErrorMsgUtils.formatMsg(entityType.name(), ExceptionType.DUPLICATE_ENTITY.getValue(),id));
    }
}
