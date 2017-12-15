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
public class DefaultFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFilter.class);

    /* (non-Javadoc)
     * @see mogujie.sql.monitor.service.focus.Filter#filter(mogujie.sql.monitor.entity.product.LogInfoProduct)
     */
    @Override
    public boolean filter(LogInfoProduct product) {
        // 当天的数据is ok
        return false;
    }
}
