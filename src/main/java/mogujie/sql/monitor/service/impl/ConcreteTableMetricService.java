/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.entity.product.TablePerformanceProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class ConcreteTableMetricService {
    private static final Logger logger = LoggerFactory.getLogger(ConcreteTableMetricService.class);

    public void consumeFocusProduct(ConcurrentMap<Long, ConcurrentMap<String, TablePerformanceProduct>> metrics,
            LogInfoProduct product) {
        Assert.isTrue(!product.getSchema().isEmpty());
        if (product.getTableName().isEmpty()) {
            logger.error("Table name is empty, SQL: {}", product.getSql());
            return;
        }
        Assert.isTrue(!product.getTableName().isEmpty());

        final Long timeKey = FormatTime.formatExecuteTime(product.getExecuteTime());
        Assert.notNull(timeKey);
        boolean needToCreateProduct = false;
        TablePerformanceProduct performanceProduct = null;

        ConcurrentMap<String, TablePerformanceProduct> tableName_2_product = null;
        if (metrics.containsKey(timeKey)) {
            tableName_2_product = metrics.get(timeKey);
            if (tableName_2_product.containsKey(product.getTableName())) {
                performanceProduct = tableName_2_product.get(product.getTableName());
            } else {
                needToCreateProduct = true;
            }
        } else {
            needToCreateProduct = true;
            tableName_2_product = new ConcurrentHashMap<>(4 * 1000);
            metrics.put(timeKey, tableName_2_product);
        }
        if (needToCreateProduct) {
            performanceProduct = new TablePerformanceProduct();
        }

        Assert.notNull(performanceProduct);
        tableName_2_product.putIfAbsent(product.getTableName(), performanceProduct);
        String format = product.getFormatSql();
        boolean select = format.startsWith("select") || format.startsWith("SELECT");
        boolean insert = format.startsWith("insert") || format.startsWith("INSERT");
        boolean update = format.startsWith("update") || format.startsWith("UPDATE");
        //
        double cost = product.getCost();
        double maxRT = Math.max(cost, performanceProduct.getMaxRT());
        performanceProduct.setMaxRT(maxRT);

        double minRT = Math.min(cost, performanceProduct.getMinRT());
        performanceProduct.setMinRT(minRT);

        String tbl = product.getTableName();
        synchronized (tbl) {
            performanceProduct.setTableName(tbl);
            performanceProduct.setQueryCount(performanceProduct.getQueryCount() + 1);
            if (select) {
                performanceProduct.setSelectCount(performanceProduct.getSelectCount() + 1);
            } else {
                if (update) {
                    performanceProduct.setUpdateCount(performanceProduct.getUpdateCount() + 1);
                } else if (insert) {
                    performanceProduct.setInsertCount(performanceProduct.getInsertCount() + 1);
                }
            }
            BigDecimal totalRT = performanceProduct.getTotalRT().add(new BigDecimal(cost, new MathContext(20)));
            performanceProduct.setTotalRT(totalRT);
            long count = performanceProduct.getQueryCount();
            final double avgRT = totalRT.divide(new BigDecimal(count), new MathContext(6)).doubleValue();
            performanceProduct.setAvgRT(avgRT);
            // TODO 95%
        }
    }

}
