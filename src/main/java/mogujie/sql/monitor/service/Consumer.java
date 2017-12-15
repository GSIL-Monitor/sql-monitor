/**
 * 
 */
package mogujie.sql.monitor.service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 * 
 */
public interface Consumer {

    /** bean configuration */
    void init();

    /** logic do job */
    void start();

    /** technical do job for Java. runnable of thread, call method: start(). */
    void run();

    void destroy();
}
