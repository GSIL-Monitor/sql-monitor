/**
 * 
 */
package mogujie.sql.monitor.service;

import java.util.List;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface MetricChain {
    void add(Metric metric);

    void remove(Metric metric);

    void setMetrics(List<Metric> metrics);
    
    List<Metric> getMetrics();
}
