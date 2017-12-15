/**
 * 
 */
package mogujie.sql.monitor.service;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TablePerformanceProduct;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface ConcreteOpsTableProtoService {
    
    String getApi();
    
    void setKeySuffix(String keySuffix);

    OpsProto build(String strTimestamp, TablePerformanceProduct performance);
}
