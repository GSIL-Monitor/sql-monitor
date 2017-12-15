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
public class TableNameFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TableNameFilter.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.focus.Filter#filter(mogujie.sql.monitor.entity
     * .product.LogInfoProduct)
     */
    @Override
    public boolean filter(LogInfoProduct product) {
        if (product.getSql() == null || product.getSql().isEmpty()) {
            return true;
        }
        if (product.isBegin() || product.isCommit() || product.isRollback()) {
            return true;
        }
        if (product.getTableName() == null || product.getTableName().isEmpty()) {
            return true;
        }
        return false;
    }
}
