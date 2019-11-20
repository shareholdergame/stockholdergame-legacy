package com.stockholdergame.server.aspects;

import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.session.UserSessionUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author Alexander Savin
 *         Date: 17.2.12 19.16
 */
@Aspect
public class FilterRemovedUserAspect {

    @Before("execution(@com.stockholdergame.server.services.security.DeniedForRemovedUser public * com.stockholdergame.server.services..*ServiceImpl.*(..))")
    public void serviceMethodAroundAdvice() throws Throwable {
        if (UserSessionUtil.getCurrentUser().isRemoved()) {
            throw new BusinessException(BusinessExceptionType.OPERATION_NOT_PERMITTED);
        }
    }
}
