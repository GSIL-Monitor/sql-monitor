/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.service.AbstractMetric;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class SuspiciousSQLMetric extends AbstractMetric {
    private static final Logger logger = LoggerFactory.getLogger(SuspiciousSQLMetric.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.Metric#consumeFocusProduct(mogujie.sql.monitor
     * .entity.product.LogInfoProduct)
     */
    @Override
    public void consumeFocusProduct(LogInfoProduct product) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.Metric#setValue(mogujie.sql.monitor.service
     * .focus.Value)
     */
    @Override
    public void setValue(Value val) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getValue()
     */
    @Override
    public Value getValue() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.Metric#setFilter(mogujie.sql.monitor.service
     * .focus.Filter)
     */
    @Override
    public void setFilter(Filter filter) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getFilter()
     */
    @Override
    public Filter getFilter() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getPurgeMap()
     */
    @Override
    public ConcurrentMap<Long, ?> getPurgeMap() {
        return null;
    }
}
