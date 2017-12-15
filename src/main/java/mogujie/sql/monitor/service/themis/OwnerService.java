/**
 * 
 */
package mogujie.sql.monitor.service.themis;

import java.util.List;

import mogujie.sql.monitor.entity.Owner;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface OwnerService {
    Owner get(String ip);

    List<Owner> getAlarmOwners(String ip);
}
