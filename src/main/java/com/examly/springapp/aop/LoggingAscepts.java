package com.examly.springapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAscepts {

    // @Pointcut("execution(* com.examly.springapp.controller..*(..))")
    // public void controllerMethods() {}

    // @Before("controllerMethods()")
    // public void logBefore() {
    //     System.out.println("Campus Recruitment System - Method called");
    // }
}
