/**
 * 
 */
package mogujie.sql.monitor.service;

import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface Metric {

    /**
     * TemplateMethod in AbstractMetric. <br/>
     * Then call {@link #consumeFocusProduct(LogInfoProduct)}
     * 
     * @param product
     */
    void consume(LogInfoProduct product);

    /**
     * concrete sub-class logical.
     * Primitive Ops
     * 
     * @param product
     */
    void consumeFocusProduct(LogInfoProduct product);

    void setValue(Value val);

    Value getValue();

    void setFilter(Filter filter);

    Filter getFilter();

    void remove(Long timeKey);

    /**
     * timeKey_2_concreteMetricModel<br />
     * concreteMetricModel: product or collection<?,product>
     * 
     * @return
     */
    ConcurrentMap<Long, ?> getPurgeMap();

}
