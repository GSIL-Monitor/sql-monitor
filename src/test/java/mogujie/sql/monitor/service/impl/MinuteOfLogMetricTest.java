package mogujie.sql.monitor.service.impl;

import java.util.Date;
import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.LogAnalyzerMetric;
import mogujie.sql.monitor.util.DateUtils;

import org.testng.annotations.Test;

public class MinuteOfLogMetricTest {
    @Test
    public void testPurgeForCleanup() {
        LogMetricInOneMinute metric = new LogMetricInOneMinute();
        @SuppressWarnings("unchecked")
        ConcurrentMap<Long, LogAnalyzerMetric> map = (ConcurrentMap<Long, LogAnalyzerMetric>) metric.getPurgeMap();
        LogAnalyzerMetric value = new LogAnalyzerMetric();
        long _201409111323_mills = 1410412980000L;
        value.setMaxTime(_201409111323_mills);
        value.setMinTime(_201409111323_mills - 10 * 60 * 1000L);
        map.put(Long.valueOf("201409111323"), value);

        value = new LogAnalyzerMetric();
        long _201409111324_mills = _201409111323_mills + 60 * 1000L;
        value.setMaxTime(_201409111324_mills);
        value.setMinTime(_201409111324_mills - 5 * 60 * 1000L);
        map.put(Long.valueOf("201409111324"), value);

        long maxCost = metric.getMaxCostInMillis();
        metric.purge();
        assert metric.getPurgeMap().isEmpty();
    }

    @Test
    public void testPurgeForImmediately() {
        LogMetricInOneMinute metric = new LogMetricInOneMinute();
        @SuppressWarnings("unchecked")
        ConcurrentMap<Long, LogAnalyzerMetric> map = (ConcurrentMap<Long, LogAnalyzerMetric>) metric.getPurgeMap();
        LogAnalyzerMetric value = new LogAnalyzerMetric();
        long now = System.currentTimeMillis();
        Long timeKey = DateUtils.formatToMinute(new Date(now));

        value.setMinTime(now - 1 * 1000);
        value.setMaxTime(now);
        value.setTimeKey(timeKey);

        map.put(timeKey, value);
        metric.purge();
        assert metric.getPurgeMap().size() == 1;

        value = new LogAnalyzerMetric();
        long _201409111323_mills = 1410412980000L;
        long _201409111324_mills = _201409111323_mills + 60 * 1000L;
        value.setMaxTime(_201409111324_mills);
        value.setMinTime(_201409111324_mills - 5 * 60 * 1000L);
        map.put(Long.valueOf("201409111324"), value);
        metric.purge();
        assert map.size() == 1;

        // exception logic
        value = new LogAnalyzerMetric();
        value.setTimeKey(timeKey);
        map.put(timeKey, value);
        assert map.size() == 1;
        LogAnalyzerMetric m = map.get(timeKey);
        System.out.println(metric.getMaxCostInMillis());
        System.out.println(m);

    }
}
