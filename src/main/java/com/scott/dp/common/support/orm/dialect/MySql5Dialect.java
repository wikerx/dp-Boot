package com.scott.dp.common.support.orm.dialect;

/**
 * MySQL数据库方言
 *
 * @author Mr.薛
 * @email *****@163.com
 * @url
 * @date 2017年8月8日 上午11:07:12
 */
public class MySql5Dialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return MySql5PageHepler.getCountString(sql);
    }
}
