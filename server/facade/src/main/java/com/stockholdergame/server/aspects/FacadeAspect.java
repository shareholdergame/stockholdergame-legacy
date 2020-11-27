package com.stockholdergame.server.aspects;

import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.INTERNAL_SERVER_ERROR;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.localization.MessageHolder;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.validation.FacadeValidator;
import com.stockholdergame.server.validation.ValidationResult;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author Alexander Savin
 *         Date: 23.12.11 23.24
 */
@Aspect
public class FacadeAspect {

    private static Logger LOGGER = Logger.getLogger(FacadeAspect.class);

    @Autowired
    private FacadeValidator validator;

    @Autowired
    private MessageChannel exceptionHandlingChannel;

    @Around("execution(public * com.stockholdergame.server.facade..*Facade.*(..))")
    public Object serviceMethodAroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        Method interfaceMethod = ((MethodSignature) jp.getSignature()).getMethod();
        Method implementationMethod = jp.getTarget().getClass().getMethod(interfaceMethod.getName(),
            interfaceMethod.getParameterTypes());
        Object[] args = jp.getArgs();

        ValidationResult validationResult = validator.validate(implementationMethod, args);
        if (!validationResult.isValid()) {
            throw new BusinessException(BusinessExceptionType.VALIDATION_FAILED, validationResult.toString());
        }

        try {
            return jp.proceed();
        } catch(Throwable throwable) {
            throw handleException(throwable);
        }
    }

    private Throwable handleException(Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);
        Locale locale = UserSessionUtil.getUserLocale();
        if(throwable instanceof BusinessException) {
            throw new BusinessException(MessageHolder.getMessage(locale, ((BusinessException) throwable).getType().getMessageKey(),
                ((BusinessException) throwable).getArgs()), ((BusinessException) throwable).getType());
        } else {
            Message message = MessageBuilder.withPayload(throwable).build();
            exceptionHandlingChannel.send(message);

            return new ApplicationException(MessageHolder.getMessage(locale, INTERNAL_SERVER_ERROR));
        }
    }
}
