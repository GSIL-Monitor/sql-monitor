/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import mogujie.sql.monitor.dao.LogConvertor;
import mogujie.sql.monitor.dao.LogMessagePuller;
import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.service.Consumer;
import mogujie.sql.monitor.service.Metric;
import mogujie.sql.monitor.service.MetricChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class ConsumerWorker implements Runnable, Consumer {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerWorker.class);

    @Resource
    private MetricChain metricChain;

    @Resource
    private LogMessagePuller logMessagePuller;

    @Resource
    private LogConvertor logConvertor;

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
                // can report to the Boss
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#init()
     */
    @Override
    public void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#start()
     */
    @Override
    public void start() {
        String line = null;
        try {
            line = logMessagePuller.next();
            if (line == null || (line = line.trim()).isEmpty()) {
                return;
            }
        } catch (InterruptedException e) {
            logger.error("Exception", e);
            return;
        }
        LogInfoProduct product = logConvertor.convertor(line);
        if (product == null) {
            return;
        }
        List<Metric> metrics = metricChain.getMetrics();
        if (metrics != null) {
            for (int i = 0; i < metrics.size(); i++) {
                Metric m = metrics.get(i);
                m.consume(product);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#destroy()
     */
    @Override
    @PreDestroy
    public void destroy() {
    }
}
