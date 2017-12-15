/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import mogujie.sql.monitor.service.Metric;
import mogujie.sql.monitor.service.MetricChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class MetricChainImpl implements MetricChain {
    private static final Logger logger = LoggerFactory.getLogger(MetricChainImpl.class);

    private List<Metric> metrics;

    /**
     * @return the metrics
     */
    public List<Metric> getMetrics() {
        return metrics;
    }

    /**
     * @param metrics
     *            the metrics to set
     */
    @Override
    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.MetricChain#add(mogujie.sql.monitor.service
     * .Metric)
     */
    @Override
    public void add(Metric metric) {
        if (null == metrics) {
            metrics = new ArrayList<>(5);
        }
        metrics.add(metric);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.MetricChain#remove(mogujie.sql.monitor.service
     * .Metric)
     */
    @Override
    public void remove(Metric metric) {
        if (null != metrics && !metrics.isEmpty()) {
            metrics.remove(metric);
        }
    }

}
