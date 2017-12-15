/**
 * 
 */
package mogujie.sql.monitor.service.themis;

import java.util.List;

import mogujie.sql.monitor.entity.Owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class OwnerImpl implements OwnerService {
    private static final Logger logger = LoggerFactory.getLogger(OwnerImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.themis.OwnerService#get(java.lang.String)
     */
    @Override
    public Owner get(String ip) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.themis.OwnerService#getAlarmOwners(java.lang
     * .String)
     */
    @Override
    public List<Owner> getAlarmOwners(String ip) {
        return null;
    }
}
