package mogujie.sql.monitor.entity.product;

import java.math.BigDecimal;
import java.math.MathContext;

import mogujie.sql.monitor.annotation.NotNull;

/**
 * 以一个timeKey归整的范围内的情况.
 */
public class TablePerformanceProduct implements Product {

    private static final long serialVersionUID = -7477147996555845123L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 时间key(归一化后20140213 0922,精确到分钟) <br/>
     * Format: yyyyMMddHHmm
     */
    @NotNull
    private Long timeKey;

    /**
     * 总调用次数. DML include: select , insert , update , delete
     */
    private volatile long queryCount = 0L;
    private volatile long selectCount = 0L;
    private volatile int insertCount = 0;
    private volatile int updateCount = 0;
    private volatile BigDecimal totalRT = new BigDecimal(0L, new MathContext(20));// 总响应时间
    private volatile double avgRT = 0D;
    private volatile double maxRT = Double.MIN_VALUE;
    private volatile double minRT = Double.MAX_VALUE;
    /**
     * 前95%请求的Average RT
     */
    private volatile double percentage95AvgRT = 0D;

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     *            the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the timeKey
     */
    public Long getTimeKey() {
        return timeKey;
    }

    /**
     * @param timeKey
     *            the timeKey to set
     */
    public void setTimeKey(Long timeKey) {
        this.timeKey = timeKey;
    }

    /**
     * @return the queryCount
     */
    public long getQueryCount() {
        return queryCount;
    }

    /**
     * @param queryCount
     *            the queryCount to set
     */
    public void setQueryCount(long queryCount) {
        this.queryCount = queryCount;
    }

    /**
     * @return the selectCount
     */
    public long getSelectCount() {
        return selectCount;
    }

    /**
     * @param selectCount
     *            the selectCount to set
     */
    public void setSelectCount(long selectCount) {
        this.selectCount = selectCount;
    }

    /**
     * @return the insertCount
     */
    public int getInsertCount() {
        return insertCount;
    }

    /**
     * @param insertCount
     *            the insertCount to set
     */
    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    /**
     * @return the updateCount
     */
    public int getUpdateCount() {
        return updateCount;
    }

    /**
     * @param updateCount
     *            the updateCount to set
     */
    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    /**
     * @return the totalRT
     */
    public BigDecimal getTotalRT() {
        return totalRT;
    }

    /**
     * @param totalRT
     *            the totalRT to set
     */
    public void setTotalRT(BigDecimal totalRT) {
        this.totalRT = totalRT;
    }

    /**
     * @return the maxRT
     */
    public double getMaxRT() {
        return maxRT;
    }

    /**
     * @param maxRT
     *            the maxRT to set
     */
    public void setMaxRT(double maxRT) {
        this.maxRT = maxRT;
    }

    /**
     * @return the minRT
     */
    public double getMinRT() {
        return minRT;
    }

    /**
     * @param minRT
     *            the minRT to set
     */
    public void setMinRT(double minRT) {
        this.minRT = minRT;
    }

    /**
     * @return the percentage95AvgRT
     */
    public double getPercentage95AvgRT() {
        return percentage95AvgRT;
    }

    /**
     * @param percentage95AvgRT
     *            the percentage95AvgRT to set
     */
    public void setPercentage95AvgRT(double percentage95AvgRT) {
        this.percentage95AvgRT = percentage95AvgRT;
    }

    /**
     * @return the avgRT
     */
    public double getAvgRT() {
        return avgRT;
    }

    /**
     * @param avgRT
     *            the avgRT to set
     */
    public void setAvgRT(double avgRT) {
        this.avgRT = avgRT;
    }

}
