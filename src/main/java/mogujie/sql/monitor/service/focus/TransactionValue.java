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
public class TransactionValue implements Value {
    private static final Logger logger = LoggerFactory.getLogger(TransactionValue.class);

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
            return true;
        }
        if (product.isBegin() || product.isCommit() || product.isRollback()) {
            return true;
        }
        return false;
    }
}
