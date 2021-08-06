package com.lhw.mysql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author lhw
 * @title
 * @description
 * @created 8/6/21 5:36 PM
 * @changeRecord
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DbContextHolder.getDbType();
        if (typeKey == DbContextHolder.WRITE) {
            LOG.info("使用了写库");
            return typeKey;
        }
        //使用读库
        LOG.info("使用了读库");
        return DbContextHolder.READ;
    }
}