/**
 * 
 */
package mogujie.sql.monitor.entity.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
public class FormatSqlProduct implements Product {
    private static final long serialVersionUID = 4694563253713369700L;

    private static final Logger logger = LoggerFactory.getLogger(FormatSqlProduct.class);

    private String formatSql = "";
    /**
     * sha1(formatSql);
     */
    private byte[] sha1 = null;

    private long total = 0L;
    private volatile double minRT = 0D;
    private volatile double maxRT = 0D;

    private volatile double percetage95AvgRT = 0D;

    /**
     * @return the formatSql
     */
    public String getFormatSql() {
        return formatSql;
    }

    /**
     * @param formatSql the formatSql to set
     */
    public void setFormatSql(String formatSql) {
        this.formatSql = formatSql;
    }

    /**
     * @return the sha1
     */
    public byte[] getSha1() {
        return sha1;
    }

    /**
     * @param sha1 the sha1 to set
     */
    public void setSha1(byte[] sha1) {
        this.sha1 = sha1;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the minRT
     */
    public double getMinRT() {
        return minRT;
    }

    /**
     * @param minRT the minRT to set
     */
    public void setMinRT(double minRT) {
        this.minRT = minRT;
    }

    /**
     * @return the maxRT
     */
    public double getMaxRT() {
        return maxRT;
    }

    /**
     * @param maxRT the maxRT to set
     */
    public void setMaxRT(double maxRT) {
        this.maxRT = maxRT;
    }

    /**
     * @return the percetage95AvgRT
     */
    public double getPercetage95AvgRT() {
        return percetage95AvgRT;
    }

    /**
     * @param percetage95AvgRT the percetage95AvgRT to set
     */
    public void setPercetage95AvgRT(double percetage95AvgRT) {
        this.percetage95AvgRT = percetage95AvgRT;
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

    
    
}
