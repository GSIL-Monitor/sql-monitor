/**
 * 
 */
package mogujie.sql.monitor.service.focus;

import mogujie.sql.monitor.entity.product.LogInfoProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class NotTransactionValue implements Value {
    private static final Logger logger = LoggerFactory.getLogger(NotTransactionValue.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.focus.Value#accept(mogujie.sql.monitor.entity
     * .product.Product)
     */
    @Override
    public boolean accept(LogInfoProduct product) {
        if (product.isInTransction()) {
            return false;
        }
        if (product.isBegin() || product.isCommit() || product.isRollback()) {
            return false;
        }
        return true;
    }
}
