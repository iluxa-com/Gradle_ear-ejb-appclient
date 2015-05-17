package com.my.interceptor;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
/**
 * Created by marcin on 18.05.15.
 */
public class Interceptor {

    @AroundInvoke
    Object logTime(InvocationContext ic) throws Exception {
        long entryTime = System.currentTimeMillis();
        Object res = null;
        try {
            res = ic.proceed();
        } finally {
            Logger.getLogger(
                    ic.getTarget().getClass().getName())
                    .debug(System.currentTimeMillis() -
                            entryTime);
            return res;
        }
    }
}
