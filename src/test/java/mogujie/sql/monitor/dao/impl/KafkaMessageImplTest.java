/**
 * 
 */
package mogujie.sql.monitor.dao.impl;

import mogujie.sql.monitor.dao.LogMessagePuller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
public class KafkaMessageImplTest {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageImplTest.class);

    @BeforeClass
    public void setUp() {
        // code that will be invoked when this test is instantiated
    }

    @Test
    public void nextTest() {
        System.out.println("Fast test");
    }

    public static void main(String[] args) throws InterruptedException {
        LogMessagePuller client = new KafkaMessagePuller();
        client.start();
        while (true) {
            String msg = client.next();
            System.out.println(msg);
        }
    }

}
