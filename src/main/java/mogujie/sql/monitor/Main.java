package mogujie.sql.monitor;

import mogujie.sql.monitor.service.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * start up
 * 
 */
public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("spring/context.xml");
            context.registerShutdownHook();

            Consumer consumer = context.getBean("consumerBoss", Consumer.class);
            consumer.start();
            logger.info("Application starts!");
        } catch (Exception e) {
            logger.error("Exception", e);
            Thread.sleep(1000);
            System.exit(-1);
        } finally {
            if (null != context) {
            }
        }
    }
}
