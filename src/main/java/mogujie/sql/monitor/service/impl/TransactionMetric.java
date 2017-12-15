/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.entity.product.TransactionPerformanceProduct;
import mogujie.sql.monitor.service.AbstractMetric;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class TransactionMetric extends AbstractMetric {
    private static final Logger logger = LoggerFactory.getLogger(TransactionMetric.class);

    /**
     * timeKey_2_map. <br/>
     * secondly map: schema_2_TransactionPerformanceProduct.
     * 
     */
    private final ConcurrentMap<Long, ConcurrentMap<String, TransactionPerformanceProduct>> metrics = new ConcurrentHashMap<>();

    private Value value;
    private Filter filter;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.Metric#consume(mogujie.sql.monitor.entity
     * .product.LogInfoProduct)
     */
    @Override
    public void consumeFocusProduct(LogInfoProduct product) {
        Assert.isTrue(!product.getSchema().isEmpty());
        if (!product.isInTransction()) {
            return;
        }

        final Long timeKey = super.formatExecuteTime(product.getExecuteTime());
        Assert.notNull(timeKey);
        TransactionPerformanceProduct p = null;
        boolean needToCreateProduct = false;
        ConcurrentMap<String, TransactionPerformanceProduct> schema_2_trx = null;
        if (metrics.containsKey(timeKey)) {
            schema_2_trx = metrics.get(timeKey);
            if (schema_2_trx.containsKey(product.getSchema())) {
                p = schema_2_trx.get(product.getSchema());
            } else {
                // new schema at that time
                needToCreateProduct = true;
            }
        } else {
            // firstly occurs !
            needToCreateProduct = true;
            schema_2_trx = new ConcurrentHashMap<>();
            metrics.putIfAbsent(timeKey, schema_2_trx);
        }
        if (needToCreateProduct) {
            p = new TransactionPerformanceProduct();
            p.setSchema(product.getSchema());
            p.setTimeKey(timeKey);
        }

        Assert.notNull(p);
        schema_2_trx.putIfAbsent(p.getSchema(), p);

        final boolean txFlag = product.isBegin() || product.isCommit() || product.isRollback();
        if (!txFlag) {
            p.getTotalSQL().incrementAndGet();
        } else {
            if (product.isBegin()) {
                p.getTotal().incrementAndGet();
            } else if (product.isCommit()) {
                p.getSuccess().incrementAndGet();
            }
        }
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
     * @see mogujie.sql.monitor.service.Metric#getValue()
     */
    @Override
    public Value getValue() {
        return this.value;
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
