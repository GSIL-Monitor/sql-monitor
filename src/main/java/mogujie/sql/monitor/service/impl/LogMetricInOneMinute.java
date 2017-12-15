/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import mogujie.sql.monitor.entity.LogAnalyzerMetric;
import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.service.AbstractMetric;
import mogujie.sql.monitor.service.MinuteLogCost;
import mogujie.sql.monitor.service.focus.Filter;
import mogujie.sql.monitor.service.focus.Value;
import mogujie.sql.monitor.service.ops.WarnService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 分析程序自身的处理能力: 一分钟的日志需要"多久"拉取完成.
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class LogMetricInOneMinute extends AbstractMetric implements MinuteLogCost {
    private static final Logger logger = LoggerFactory.getLogger(LogMetricInOneMinute.class);

    public static final int DEFAULT_COST_IN_MILLI = 2 * 60 * 1000;

    /**
     * timeKey_2_LogAnalyzerMetric
     */
    private final ConcurrentMap<Long, LogAnalyzerMetric> metrics = new ConcurrentHashMap<>(10 * 4 / 3 + 1);

    @Resource
    private WarnService warnService;

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
    @Async
    public void consumeFocusProduct(LogInfoProduct product) {
        Long timeKey = formatExecuteTime(product.getExecuteTime());

        LogAnalyzerMetric metric = null;
        if (metrics.containsKey(timeKey)) {
            metric = metrics.get(timeKey);
        } else {
            metric = new LogAnalyzerMetric();
            metric.setTimeKey(timeKey);
            metrics.putIfAbsent(timeKey, metric);
        }

        long now = System.currentTimeMillis();
        long maxTime = metric.getMaxTime();
        maxTime = Math.max(now, maxTime);
        metric.setMaxTime(maxTime);

        long minTime = metric.getMinTime();
        minTime = Math.min(now, minTime);
        metric.setMinTime(minTime);
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

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.impl.AbstractMetric#purge()
     */
    @Override
    public void purge() {
        final long now = System.currentTimeMillis();
        // big cost
        List<Entry<Long, LogAnalyzerMetric>> entries = new ArrayList<>(metrics.entrySet());
        for (Entry<Long, LogAnalyzerMetric> e : entries) {
            final LogAnalyzerMetric m = e.getValue();
            long cost = m.getCost();
            if (cost <= 30 * 1000) {
                cost = DEFAULT_COST_IN_MILLI;
            }
            if (m.getMaxTime() + cost * 2 < now) {
                // clear
                metrics.remove(e.getKey());
            }
        }
        // TODO too large -> warn
        if (metrics.size() > 15) {
            // mail warning...
            logger.error("App has some data over 15 minutes that not sent!");
        }
        entries.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.MinuteLogCost#getMaxCostInMillis()
     */
    @Override
    public long getMaxCostInMillis() {
        // 避免程序刚启动的情况. max计算出一个 <= 0
        long max = DEFAULT_COST_IN_MILLI;
        Iterator<LogAnalyzerMetric> it = metrics.values().iterator();
        while (it.hasNext()) {
            LogAnalyzerMetric m = it.next();
            if (null != m) {
                max = Math.max(max, m.getCost());
            }
        }
        return max;
    }

}
