/**
 * 
 */
package mogujie.sql.monitor.dao;

/**
 * Implements thread-safe
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
public interface LogMessagePuller {
    /**
     * @return null or log
     * @throws InterruptedException
     */
    String next() throws InterruptedException;

    /**
     * @Async
     */
    void start();

    void stop();

    /**
     * @return the size of messages in the queue
     */
    int size();
}
