package com.selenium.asxnews.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Aspect
@Component
public class AOPInterceptor {

    @Autowired
    ArrayList<LocalDate> holidays;
    /*
    If days is in holiday then dont run.
     */
    @Around("execution(void com.selenium.asxnews.sched.DailySched.run())")
    public void employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("Before invoking getName() method");



        Object value = null;
        try {
            System.out.println("CALL 1");
            if(!holidays.contains(LocalDate.now())){
                System.out.println(" RUN ");
                proceedingJoinPoint.proceed();

            }

            //
            System.out.println("CALL 2");

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }




}
