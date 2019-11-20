package com.stockholdergame.server.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Benchmark aspect.
 *
 * @author Alexander Savin
 */
@Aspect
public class BenchmarkAspect {

    private static Logger LOGGER = LogManager.getLogger(BenchmarkAspect.class);

    @Around("execution(public * com.stockholdergame.server.services.game.PlayGameService.doMove(..))")
    public Object serviceMethodAroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = jp.proceed();
        long endTime = System.currentTimeMillis();
        double secs = ((double) (endTime) - startTime) / 1000;
        LOGGER.info("doMove total time: " + secs);
        return result;
    }
}
