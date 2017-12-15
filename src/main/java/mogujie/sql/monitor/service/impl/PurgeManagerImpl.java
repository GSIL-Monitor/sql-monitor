/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import mogujie.sql.monitor.annotation.Asynchronous;
import mogujie.sql.monitor.entity.LogAnalyzerMetric;
import mogujie.sql.monitor.service.MinuteLogCost;
import mogujie.sql.monitor.service.OpsImplAbastraction;
import mogujie.sql.monitor.service.PurgeManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class PurgeManagerImpl implements PurgeManager {
    private static final Logger logger = LoggerFactory.getLogger(PurgeManagerImpl.class);

    @Resource(name = "logMetricInOneMinute")
    private MinuteLogCost minuteLogCost;

    @Resource(name = "notTxTableProtoImplAbstraction")
    private OpsImplAbastraction notTxTableProtoImplAbstraction;

    @Resource(name = "onlyTxTableProtoImplAbstraction")
    private OpsImplAbastraction onlyTxTableProtoImplAbstraction;

    @Resource(name = "txSchemaLevelProtoImplAbstraction")
    private OpsImplAbastraction txSchemaLevelProtoImplAbstraction;

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.PurgeManager#start()
     */
    @Override
    @Asynchronous
    @PostConstruct
    public void start() {
        PurgeTask task = new PurgeTask();
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        final String name = this.getClass().getName();
        final Thread th = new Thread(group, task, name);
        th.setPriority(Thread.MAX_PRIORITY);
        th.start();
    }

    private boolean purge(long execute, long cost) {
        if (execute + cost * 0.75 < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    private class PurgeTask implements Runnable {

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            while (true) {
                try {
                    start();
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    logger.error("Exception", e);
                    Thread.interrupted();
                }
            }
        }

        public void start() {
            final long maxCost = minuteLogCost.getMaxCostInMillis();

            final List<Long> timeKeys = new ArrayList<>(minuteLogCost.getPurgeMap().keySet());
            if (timeKeys.isEmpty()) {
                return;
            }
            logger.info("Now has {} timeKeys.", timeKeys.size());
            for (Long timeKey : timeKeys) {
                LogAnalyzerMetric m = (LogAnalyzerMetric) minuteLogCost.getPurgeMap().get(timeKey);
                if (purge(m.getMaxTime(), maxCost)) {
                    final long l_timeKey = timeKey.longValue();
                    notTxTableProtoImplAbstraction.purge(l_timeKey);
                    onlyTxTableProtoImplAbstraction.purge(l_timeKey);
                    txSchemaLevelProtoImplAbstraction.purge(l_timeKey);
                    minuteLogCost.remove(timeKey);
                    logger.info("The timeKey is {}", timeKey);
                }
            }
            logger.info("After purge task. It has {} timeKeys.", minuteLogCost.getPurgeMap().size());
        }
    }
}
