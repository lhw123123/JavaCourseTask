package com.lhw.mysql.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author lhw
 * @title
 * @description
 * @created 8/6/21 5:40 PM
 * @changeRecord
 */
@Aspect
@Component
public class ReadOnlyInterceptor implements Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(ReadOnlyInterceptor.class);

    @Around("@annotation(readOnly)")
    public Object setWrite(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable {
        try {
            DbContextHolder.setDbType(DbContextHolder.READ);
            return joinPoint.proceed();
        } finally {
            //清楚DbType一方面为了避免内存泄漏，更重要的是避免对后续在本线程上执行的操作产生影响
            DbContextHolder.clearDbType();
            LOG.info("清除threadLocal");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
