/**
 * 
 */
package mogujie.sql.monitor.service.impl.ops.bridge;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.product.TransactionPerformanceProduct;
import mogujie.sql.monitor.service.ConcreteOpsSchemaProtoService;
import mogujie.sql.monitor.service.Metric;
import mogujie.sql.monitor.service.OpsProtoImplementor;
import mogujie.sql.monitor.service.ops.OpsService;
import mogujie.sql.monitor.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理Transaction Data
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class OpsTxProtoImplementor implements OpsProtoImplementor {
    private static final Logger logger = LoggerFactory.getLogger(OpsTxProtoImplementor.class);

    private Metric metric;

    private OpsService opsService;

    private List<ConcreteOpsSchemaProtoService> concreteOpsSchemaProtoServices;

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.OpsProtoImplementor#purgeImpl(long)
     */
    @Override
    public void purgeImpl(long timeKey) {
        // 明确知道被调用方的数据类型.
        @SuppressWarnings("unchecked")
        ConcurrentMap<String, TransactionPerformanceProduct> schema_2_trx = (ConcurrentMap<String, TransactionPerformanceProduct>) metric
                .getPurgeMap().get(Long.valueOf(timeKey));
        if (null != schema_2_trx) {
            List<String> schemas = new ArrayList<>(schema_2_trx.keySet());
            if (schemas.isEmpty()) {
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
                for (String schema : schemas) {
                    handleOneSchema(schema_2_trx, strTimestamp, schema);
                } // end loop
                logger.debug("Send timeKey:{} schema-size:{}", Long.valueOf(timeKey), schemas.size());
                this.remove(timeKey);
            }
        }
    }

    /**
     * @param schema_2_trx
     * @param strTimestamp
     * @param schema
     */
    private void handleOneSchema(ConcurrentMap<String, TransactionPerformanceProduct> schema_2_trx,
            String strTimestamp, String schema) {
        TransactionPerformanceProduct performance = schema_2_trx.get(schema);
        if (null == performance) {
            return;
        }
        // send && remove
        // one table , one TablePerformanceProduct -> many options
        try {
            for (ConcreteOpsSchemaProtoService svr : concreteOpsSchemaProtoServices) {
                OpsProto opsProto = svr.build(strTimestamp, performance);
                send(opsProto);
                opsProto.getParams().clear();
                opsProto = null;
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            schema_2_trx.remove(schema);
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
        return opsService.send(opsProto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.OpsProtoImplementor#remove(long)
     */
    @Override
    public void remove(long timeKey) {
        this.metric.remove(Long.valueOf(timeKey));
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
     * @return the concreteOpsSchemaProtoServices
     */
    public List<ConcreteOpsSchemaProtoService> getConcreteOpsSchemaProtoServices() {
        return concreteOpsSchemaProtoServices;
    }

    /**
     * @param concreteOpsSchemaProtoServices
     *            the concreteOpsSchemaProtoServices to set
     */
    public void setConcreteOpsSchemaProtoServices(List<ConcreteOpsSchemaProtoService> concreteOpsSchemaProtoServices) {
        this.concreteOpsSchemaProtoServices = concreteOpsSchemaProtoServices;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

}
