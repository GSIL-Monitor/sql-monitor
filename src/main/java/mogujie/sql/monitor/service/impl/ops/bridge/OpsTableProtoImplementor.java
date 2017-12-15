/**
 * 
 */
package mogujie.sql.monitor.service.impl.ops.bridge;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TablePerformanceProduct;
import mogujie.sql.monitor.service.ConcreteOpsTableProtoService;
import mogujie.sql.monitor.service.Metric;
import mogujie.sql.monitor.service.OpsProtoImplementor;
import mogujie.sql.monitor.service.ops.OpsService;
import mogujie.sql.monitor.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class OpsTableProtoImplementor implements OpsProtoImplementor {
    private static final Logger logger = LoggerFactory.getLogger(OpsTableProtoImplementor.class);
    private static final Logger opsRequestLogger = LoggerFactory.getLogger("detail_ops_request");

    private Metric metric;

    private OpsService opsService;

    private List<ConcreteOpsTableProtoService> concreteOpsTableProtoServices;

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.OpsProtoImplementor#purgeImpl(long)
     */
    @Override
    public void purgeImpl(long timeKey) {
        @SuppressWarnings("unchecked")
        ConcurrentMap<String, TablePerformanceProduct> tableName_2_product = (ConcurrentMap<String, TablePerformanceProduct>) metric
                .getPurgeMap().get(Long.valueOf(timeKey));
        if (null != tableName_2_product) {
            List<String> tables = new ArrayList<>(tableName_2_product.keySet());
            if (tables.isEmpty()) {
                return;
            } else {
                long timestamp;
                try {
                    timestamp = DateUtils.getTimestampFromMinute(String.valueOf(timeKey));
                } catch (ParseException e) {
                    logger.error("Exception", e);
                    return;
                }
                String strTimestamp = String.valueOf(timestamp);
                for (String table : tables) {
                    handleOneTable(tableName_2_product, strTimestamp, table);
                } // end loop
                logger.debug("Send timeKey:{} table-size:{}", Long.valueOf(timeKey), tables.size());
                this.remove(timeKey);
            }
        }
    }

    /**
     * @param tableName_2_product
     * @param strTimestamp
     * @param table
     */
    private void handleOneTable(ConcurrentMap<String, TablePerformanceProduct> tableName_2_product,
            String strTimestamp, String table) {
        TablePerformanceProduct performance = tableName_2_product.get(table);
        if (null == performance) {
            return;
        }
        // send && remove
        // one table , one TablePerformanceProduct -> many options
        try {
            for (ConcreteOpsTableProtoService svr : concreteOpsTableProtoServices) {
                OpsProto opsProto = svr.build(strTimestamp, performance);
                send(opsProto);
                opsProto.getParams().clear();
                opsProto = null;
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            tableName_2_product.remove(table);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.OpsProtoImplementor#send(mogujie.sql.monitor
     * .entity.ops.OpsProto)
     */
    @Override
    public boolean send(OpsProto opsProto) {
        // Just for debugging
        // List<NameValuePair> params = opsProto.getParams();
        // String key = "", value = "", time = "";
        //
        // for (NameValuePair pair : params) {
        // if (pair.getName().equals("key")) {
        // key = pair.getValue();
        // } else if (pair.getName().equals("val")) {
        // value = pair.getValue();
        // } else if (pair.getName().equals("time")) {
        // time = pair.getValue();
        // }
        // }
        // opsRequestLogger.info("time: {} val: {} key: {}", time, value, key);
        return opsService.send(opsProto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.OpsProtoImplementor#remove(long)
     */
    @Override
    public void remove(long timeKey) {
        metric.remove(Long.valueOf(timeKey));
    }

    /**
     * @return the metric
     */
    public Metric getMetric() {
        return metric;
    }

    /**
     * @param metric
     *            the metric to set
     */
    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    /**
     * @return the opsService
     */
    public OpsService getOpsService() {
        return opsService;
    }

    /**
     * @param opsService
     *            the opsService to set
     */
    public void setOpsService(OpsService opsService) {
        this.opsService = opsService;
    }

    /**
     * @return the concreteOpsTableProtoServices
     */
    public List<ConcreteOpsTableProtoService> getConcreteOpsTableProtoServices() {
        return concreteOpsTableProtoServices;
    }

    /**
     * @param concreteOpsTableProtoServices
     *            the concreteOpsTableProtoServices to set
     */
    public void setConcreteOpsTableProtoServices(List<ConcreteOpsTableProtoService> concreteOpsTableProtoServices) {
        this.concreteOpsTableProtoServices = concreteOpsTableProtoServices;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @return the opsrequestlogger
     */
    public static Logger getOpsrequestlogger() {
        return opsRequestLogger;
    }

}
