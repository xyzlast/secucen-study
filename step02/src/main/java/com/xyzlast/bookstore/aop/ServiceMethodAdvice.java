package com.xyzlast.bookstore.aop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;

import java.lang.reflect.Method;

public class ServiceMethodAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + " is called");
    }
}
