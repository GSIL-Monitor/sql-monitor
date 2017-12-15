/**
 * 
 */
package mogujie.sql.monitor.service;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TransactionPerformanceProduct;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface ConcreteOpsSchemaProtoService {

    String getApi();

    void setKeySuffix(String keySuffix);

    OpsProto build(String strTimestamp, TransactionPerformanceProduct performance);
}
