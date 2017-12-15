/**
 * 
 */
package mogujie.sql.monitor.entity;

import mogujie.sql.monitor.annotation.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class LogAnalyzerMetric implements java.io.Serializable {
    private static final long serialVersionUID = -679496971042480687L;

    private static final Logger logger = LoggerFactory.getLogger(LogAnalyzerMetric.class);

    /**
     * 分析程序实际最早的local time
     */
    private volatile long minTime = Long.MAX_VALUE;
    /**
     * 分析程序实际最后的local time
     */
    private volatile long maxTime = Long.MIN_VALUE;
    /**
     * 被分析的日志是属于"哪一分钟"
     */
    private Long timeKey;

    /**
     * @return the minTime
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * @param minTime
     *            the minTime to set
     */
    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    /**
     * @return the maxTime
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * @param maxTime
     *            the maxTime to set
     */
    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
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
    @NotNull
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
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    public final long getCost() {
        return maxTime - minTime;
    }

}
