/**
 * 
 */
package mogujie.sql.monitor.service.impl.ops.bridge;

import mogujie.sql.monitor.service.OpsImplAbastraction;
import mogujie.sql.monitor.service.OpsProtoImplementor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class RefineOpsImplAbastraction implements OpsImplAbastraction {
    private static final Logger logger = LoggerFactory.getLogger(RefineOpsImplAbastraction.class);

    private OpsProtoImplementor impl;

    public RefineOpsImplAbastraction(OpsProtoImplementor impl) {
        this.impl = impl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.OpsImplAbastraction#purge(long)
     */
    @Override
    public void purge(long timeKey) {
        impl.purgeImpl(timeKey);
    }
}
