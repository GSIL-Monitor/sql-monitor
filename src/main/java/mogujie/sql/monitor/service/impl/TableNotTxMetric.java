/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.entity.product.TablePerformanceProduct;
import mogujie.sql.monitor.service.AbstractMetric;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class TableNotTxMetric extends AbstractMetric {
    private static final Logger logger = LoggerFactory.getLogger(TableNotTxMetric.class);

    /**
     * timeKey_2_map. <br/>
     * secondly map: tableName_2_TablePerformanceProduct.
     * 
     */
    private final ConcurrentMap<Long, ConcurrentMap<String, TablePerformanceProduct>> metrics = new ConcurrentHashMap<>(
            10);

    @Resource(name = "tableNameFilter")
    private Filter filter;

    @Resource(name = "notTransactionValue")
    private Value value;

    @Resource(name = "concreteTableMetricService")
    private ConcreteTableMetricService metricService;

    @Override
    public void consumeFocusProduct(LogInfoProduct product) {
        metricService.consumeFocusProduct(metrics, product);
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
        this.value = val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getValue()
     */
    @Override
    public Value getValue() {
        return this.value;
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
        this.filter = filter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getFilter()
     */
    @Override
    public Filter getFilter() {
        return this.filter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Metric#getPurgeMap()
     */
    @Override
    public ConcurrentMap<Long, ?> getPurgeMap() {
        return this.metrics;
    }

}
