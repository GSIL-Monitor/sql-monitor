/**
 * 
 */
package mogujie.sql.monitor.service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface OpsImplAbastraction {

    /**
     * send the data and remove.
     * 
     * @param timeKey
     */
    void purge(long timeKey);

}
