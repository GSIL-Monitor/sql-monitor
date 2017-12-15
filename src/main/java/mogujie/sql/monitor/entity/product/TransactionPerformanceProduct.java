/**
 * 
 */
package mogujie.sql.monitor.entity.product;

import java.util.concurrent.atomic.AtomicInteger;

import mogujie.sql.monitor.annotation.NotNull;

/**
 * 以一个timeKey归整的范围内的情况.
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
public class TransactionPerformanceProduct implements Product {

    private static final long serialVersionUID = -4243141168529336157L;
    private String schema;
    /**
     * 时间key(归一化后20140213 0922,精确到分钟)<br/>
     * Format: yyyyMMddHHmm
     */
    @NotNull
    private Long timeKey;
    /**
     * 事务下关联的单条DML SQL,总个数.
     */
    private final AtomicInteger totalSQL = new AtomicInteger(0);
    private final AtomicInteger total = new AtomicInteger(0);
    private final AtomicInteger success = new AtomicInteger(0);
    /**
     * failure = total - success
     */
    private final int failure = -1;

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema
     *            the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the totalSQL
     */
    public AtomicInteger getTotalSQL() {
        return totalSQL;
    }

    /**
     * @return the total
     */
    public AtomicInteger getTotal() {
        return total;
    }

    /**
     * @return the success
     */
    public AtomicInteger getSuccess() {
        return success;
    }

    /**
     * @return the failure
     */
    public int getFailure() {
        return total.get() - success.get();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((schema == null) ? 0 : schema.hashCode());
        result = prime * result + success.get();
        result = prime * result + ((timeKey == null) ? 0 : timeKey.hashCode());
        result = prime * result + total.get();
        result = prime * result + totalSQL.get();
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransactionPerformanceProduct other = (TransactionPerformanceProduct) obj;
        if (schema == null) {
            if (other.schema != null)
                return false;
        } else if (!schema.equals(other.schema))
            return false;
        if (timeKey == null) {
            if (other.timeKey != null)
                return false;
        } else if (!timeKey.equals(other.timeKey))
            return false;

        if (other.success.get() != success.get())
            return false;
        if (other.total.get() != total.get())
            return false;
        if (other.totalSQL.get() != totalSQL.get())
            return false;
        return true;
    }

}
