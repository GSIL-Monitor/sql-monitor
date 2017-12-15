/**
 * 
 */
package mogujie.sql.monitor.service.impl.schema;

import java.util.ArrayList;
import java.util.List;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TransactionPerformanceProduct;
import mogujie.sql.monitor.service.ConcreteOpsSchemaProtoService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class TotalTransactionPerMinuteOpsServiceImpl implements ConcreteOpsSchemaProtoService {
    private static final Logger logger = LoggerFactory.getLogger(TotalTransactionPerMinuteOpsServiceImpl.class);
    private String keySuffix;

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.ConcreteOpsSchemaProtoService#getApi()
     */
    @Override
    public String getApi() {
        return "/upload.php";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ConcreteOpsSchemaProtoService#setKeySuffix
     * (java.lang.String)
     */
    @Override
    public void setKeySuffix(String keySuffix) {
        this.keySuffix = keySuffix;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ConcreteOpsSchemaProtoService#build(java.
     * lang.String,
     * mogujie.sql.monitor.entity.product.TransactionPerformanceProduct)
     */
    @Override
    public OpsProto build(String strTimestamp, TransactionPerformanceProduct performance) {
        List<NameValuePair> httpParams = new ArrayList<>();
        NameValuePair e = null;

        String uploadKey = "SQL-MONITOR_" + performance.getSchema() + this.keySuffix;
        e = new BasicNameValuePair("key", uploadKey);
        httpParams.add(e);

        e = new BasicNameValuePair("val", String.valueOf(performance.getTotal()));
        httpParams.add(e);

        e = new BasicNameValuePair("type", "3");
        httpParams.add(e);

        e = new BasicNameValuePair("time", strTimestamp);
        httpParams.add(e);

        OpsProto opsProto = new OpsProto(getApi(), httpParams);
        return opsProto;
    }
}
