package com.scott.dp.common.support.orm.dialect;

/**
 * MSSQL 数据库方言
 *
 * @author Mr.薛
 * @email *****@163.com
 * @url
 * @date 2017年8月8日 上午110650
 */
public class MSDialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MSPageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return MSPageHepler.getCountString(sql);
    }
}
