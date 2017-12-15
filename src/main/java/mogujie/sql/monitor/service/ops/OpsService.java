/**
 * 
 */
package mogujie.sql.monitor.service.ops;

import mogujie.sql.monitor.entity.ops.OpsProto;

/**
 * 相同的一个timeKey下的内容只发送一次, 要起到保护http资源作用.
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface OpsService {

    /**
     * @param opsProto
     * @return
     */
    boolean send(OpsProto opsProto);

    long successCount();

    long failCount();
}
