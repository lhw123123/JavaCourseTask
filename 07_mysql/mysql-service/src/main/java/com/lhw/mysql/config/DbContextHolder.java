package com.lhw.mysql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lhw
 * @title
 * @description
 * @created 8/6/21 5:38 PM
 * @changeRecord
 */
public class DbContextHolder {

    private static final Logger LOG = LoggerFactory.getLogger(DbContextHolder.class);
    public static final String WRITE = "write";
    public static final String READ = "read";

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (dbType == null) {
            LOG.error("dbType为空");
            throw new NullPointerException();
        }
        LOG.info("设置dbType为:{}", dbType);
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get() == null ? WRITE : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

}
