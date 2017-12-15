/**
 * 
 */
package mogujie.sql.monitor.service.ops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class WarnServiceImpl implements WarnService {
    private static final Logger logger = LoggerFactory.getLogger(WarnServiceImpl.class);

    /* (non-Javadoc)
     * @see mogujie.sql.monitor.service.ops.WarnService#smsWarn(java.lang.String, java.lang.String)
     */
    @Override
    public void smsWarn(String receivers, String content) {
    }

    /* (non-Javadoc)
     * @see mogujie.sql.monitor.service.ops.WarnService#mailWarn(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void mailWarn(String receivers, String subject, String content) {
    }
}
