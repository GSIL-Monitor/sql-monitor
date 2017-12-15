/**
 * 
 */
package mogujie.sql.monitor.entity.product;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class CountSqlProduct implements Product {
    private static final long serialVersionUID = 7016691850049614706L;

    private static final Logger logger = LoggerFactory.getLogger(CountSqlProduct.class);
    
    private final List<FormatSqlProduct> formateSQLs = new ArrayList<FormatSqlProduct>();
}
