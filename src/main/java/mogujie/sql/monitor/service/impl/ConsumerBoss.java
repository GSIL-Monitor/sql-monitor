/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import mogujie.sql.monitor.config.AppConfig;
import mogujie.sql.monitor.dao.LogMessagePuller;
import mogujie.sql.monitor.service.Consumer;
import mogujie.sql.monitor.util.NamedThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
@Service
public class ConsumerBoss implements Consumer {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerBoss.class);

    @Resource
    private LogMessagePuller logMessagePuller;

    public static int DEFAULT_WORKER_SIZE = Runtime.getRuntime().availableProcessors();
    private int workersCpuMultiple = 2;

    private final ExecutorService pool;

    private List<ConsumerWorker> workers = new ArrayList<>();

    @Resource
    private AppConfig appConfig;

    @Autowired
    private ApplicationContext ctx;

    public ConsumerBoss() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("consumerWorker");
        pool = Executors.newFixedThreadPool(DEFAULT_WORKER_SIZE, threadFactory);
    }

    @Autowired(required = true)
    public void setWorkersCpuMultiple(@Value("${workers.cpu.multiple}") int workersCpuMultiple) {
        this.workersCpuMultiple = workersCpuMultiple;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#init()
     */
    @Override
    @PostConstruct
    public void init() {
        // initialize multiple workers
        int workerSize = 1;
        if (!appConfig.isDebug()) {
            workerSize = DEFAULT_WORKER_SIZE * this.workersCpuMultiple;
        }

        logger.info("WorkerSize : {}", String.valueOf(workerSize));
        for (int i = 0; i < workerSize; i++) {
            ConsumerWorker worker = ctx.getBean("consumerWorker", ConsumerWorker.class);
            workers.add(worker);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#start()
     */
    @Override
    public void start() {
        logMessagePuller.start();

        for (ConsumerWorker w : workers) {
            pool.submit(w);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#run()
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.Consumer#destroy()
     */
    @Override
    @PreDestroy
    public void destroy() {
        workers.clear();
        if (null != pool) {
            pool.shutdown();
        }
    }
}
