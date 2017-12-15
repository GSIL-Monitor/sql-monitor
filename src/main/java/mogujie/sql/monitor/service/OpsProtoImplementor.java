/**
 * 
 */
package mogujie.sql.monitor.service;

import mogujie.sql.monitor.entity.ops.OpsProto;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface OpsProtoImplementor {
    /**
     * send the data and remove
     * 
     * @param timeKey
     */
    void purgeImpl(long timeKey);

    boolean send(OpsProto opsProto);

    void remove(long timeKey);
}
