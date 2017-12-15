/**
 * 
 */
package mogujie.sql.monitor.service;

import java.util.Date;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;
import mogujie.sql.monitor.service.impl.FormatTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public abstract class AbstractMetric implements Metric {
    private static final Logger logger = LoggerFactory.getLogger(AbstractMetric.class);

    public Long formatExecuteTime(Date executeTime) {
        return FormatTime.formatExecuteTime(executeTime);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.Metric#consume(mogujie.sql.monitor.entity
     * .product.LogInfoProduct)
     */
    @Override
    public void consume(LogInfoProduct product) {
        Assert.notNull(product);
        final Filter filter = getFilter();
        if (null != filter) {
            boolean doFilter = filter.filter(product);
            if (doFilter) {
                return;
            }
        }
        final Value value = getValue();
        if (null != value) {
            boolean doVal = value.accept(product);
            if (!doVal) {
                return;
            }
        }
        consumeFocusProduct(product);
    }

    @Override
    public void remove(Long timeKey) {
        getPurgeMap().remove(timeKey);
    }
}
