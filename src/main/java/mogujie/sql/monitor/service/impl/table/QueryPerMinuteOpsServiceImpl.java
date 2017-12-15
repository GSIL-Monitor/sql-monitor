/**
 * 
 */
package mogujie.sql.monitor.service.impl.table;

import java.util.ArrayList;
import java.util.List;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TablePerformanceProduct;
import mogujie.sql.monitor.service.ConcreteOpsTableProtoService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class QueryPerMinuteOpsServiceImpl implements ConcreteOpsTableProtoService {
    private static final Logger logger = LoggerFactory.getLogger(QueryPerMinuteOpsServiceImpl.class);

    private String keySuffix = "";

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ConcreteOpsTableProtoService#setApi(java.
     * lang.String)
     */
    @Override
    public String getApi() {
        return "/upload.php";
    }

    /*
     * // TODO modify real key and value (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ConcreteOpsTableProtoService#build(mogujie
     * .sql.monitor.entity.product.TablePerformanceProduct)
     */
    @Override
    public OpsProto build(String strTimestamp, TablePerformanceProduct performance) {
        List<NameValuePair> httpParams = new ArrayList<>();
        NameValuePair e = null;

        String uploadKey = "SQL-MONITOR_" + performance.getTableName() + this.keySuffix;
        e = new BasicNameValuePair("key", uploadKey);
        httpParams.add(e);

        e = new BasicNameValuePair("val", String.valueOf(performance.getQueryCount()));
        httpParams.add(e);

        e = new BasicNameValuePair("type", "3");
        httpParams.add(e);

        e = new BasicNameValuePair("time", strTimestamp);
        httpParams.add(e);

        OpsProto opsProto = new OpsProto(getApi(), httpParams);
        return opsProto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ConcreteOpsTableProtoService#setKeySuffix
     * (java.lang.String)
     */
    @Override
    public void setKeySuffix(String keySuffix) {
        this.keySuffix = keySuffix;
    }
}
