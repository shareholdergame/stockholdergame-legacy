package com.stockholdergame.server.web.advice;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.web.dto.ErrorBody;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler(ApplicationException.class)
    public @ResponseBody ResponseWrapper<ErrorBody> handleApplicationException(ApplicationException ae) {
        return ResponseWrapper.error(ErrorBody.of(ae.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public @ResponseBody ResponseWrapper<ErrorBody> handleBusinessException(BusinessException be) {
        return ResponseWrapper.error(ErrorBody.of(be.getMessage()));
    }
}
