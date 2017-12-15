/**
 * 
 */
package mogujie.sql.monitor.dao.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PreDestroy;

import mogujie.sql.monitor.dao.LogMessagePuller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mogujie.jafka.client.KafkaConsumer;

/**
 * This is thread-safe.
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
@Repository
public class KafkaMessagePuller implements LogMessagePuller {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessagePuller.class);

    private final int size = 1000 / Runtime.getRuntime().availableProcessors()
            * Runtime.getRuntime().availableProcessors();

    private final BlockingQueue<String> msgs = new ArrayBlockingQueue<String>(size);

    /**
     * constraint with @author @柱子 zhuzi@mogujie.com
     */
    private final KafkaConsumer kafka = new KafkaConsumer("db_group");
    private String topics = "";

    /**
     * @return the topics
     */
    public String getTopics() {
        return topics;
    }

    /**
     * @param topics
     *            the topics to set
     */
    @Autowired(required = true)
    public void setTopics(@Value("${topics}") String topics) {
        this.topics = topics;
    }

    /**
     * Will block
     * 
     * @see mogujie.sql.monitor.dao.LogMessagePuller#next()
     */
    @Override
    public String next() throws InterruptedException {
        return msgs.take();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.datasource.LogMessagePuller#start()
     */
    @Override
    public void start() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();

        String[] ts = this.topics.split(",");
        if (ts == null || ts.length < 1) {
            return;
        }

        for (String topic : ts) {
            if (topic.isEmpty()) {
                continue;
            }
            final KafkaTask target = new KafkaTask(kafka, topic, msgs);
            final String name = this.getClass().getName();
            final Thread th = new Thread(group, target, name);
            th.setPriority(Thread.MIN_PRIORITY);
            th.start();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.datasource.LogMessagePuller#stop()
     */
    @Override
    @PreDestroy
    public void stop() {
        kafka.shutdown();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.datasource.LogMessagePuller#size()
     */
    @Override
    public int size() {
        return msgs.size();
    }

    private class KafkaTask implements Runnable {
        private final KafkaConsumer client;
        private final BlockingQueue<String> container;
        private String topic = "";

        /**
         * @param client
         * @param topics
         *            TODO
         * @param container
         */
        private KafkaTask(KafkaConsumer client, String topics, BlockingQueue<String> container) {
            super();
            this.client = client;
            this.topic = topics;
            this.container = container;
        }

        /*
         * 
         * constraint with @author @柱子 zhuzi@mogujie.com
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            while (true) {
                String log = null;
                try {
                    // kafka blocking
                    log = client.get(this.topic);
                } catch (Exception e) {
                    // this.kafka.shutdown();
                    logger.error("Exception", e);
                }
                try {
                    assert null != log;
                    if (null == log) {
                        Thread.sleep(300);
                    } else {
                        container.put(log);
                    }
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
        }
    }
}
