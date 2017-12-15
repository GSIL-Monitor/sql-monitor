/**
 * 
 */
package mogujie.sql.monitor.service;

import java.util.concurrent.ConcurrentMap;

/**
 * 本程序一分钟里的日志,从string到java object的消耗时间
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface MinuteLogCost {

    void purge();

    /**
     * @return 分析每分钟的日志最大消耗的时间->动态变化.
     */
    long getMaxCostInMillis();

    ConcurrentMap<Long, ?> getPurgeMap();

    void remove(Long timeKey);
}
