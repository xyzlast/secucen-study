package com.xyzlast.bookstore.aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

@Aspect
public class ServiceAdvisor {

    @Pointcut(value = "@annotation(com.xyzlast.bookstore.aop.ServiceAopMethod)")
    public void serviceMethodPointCut() {

    }

    @Before("serviceMethodPointCut()")
    public void beforeTargetMethod(JoinPoint thisJoinPoint) {
        System.out.println("beforeTargetMethod");
    }

    @AfterReturning(pointcut = "serviceMethodPointCut()", returning = "retVal")
    public void afterReturningTargetMethod(JoinPoint thisJoinPoint, Object retVal) {
        System.out.println("AspectUsingAnnotation.afterReturningTargetMethod executed." +
            " return value is [" + retVal + "]");
    }

    @AfterThrowing(pointcut = "serviceMethodPointCut()", throwing = "exception")
    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint,
                                          Exception exception) throws Exception {
        System.out.println("AspectUsingAnnotation.afterThrowingTargetMethod executed.");
        System.out.println("에러가 발생했습니다." + exception.getMessage());
        throw new RuntimeException("에러가 발생했습니다.", exception);
    }

    @After(value = "serviceMethodPointCut()")
    public void after(JoinPoint thisJoinPoint) {
        System.out.println("AspectUsing After");
    }

    @Around("serviceMethodPointCut()")
    public Object wrapResponseObject(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("before: ");
        Object ret = pjp.proceed();
        System.out.println("after: ");
        return ret;
    }
}
