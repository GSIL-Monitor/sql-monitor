package mogujie.sql.monitor.dao;

import mogujie.sql.monitor.entity.product.LogInfoProduct;

/**
 * 日志转换器
 * 
 * @author luoqi luoqi@mogujie.com
 * @date 2014年2月26日 下午1:52:59
 */
public interface LogConvertor {

    /**
     * 将一行Log转换成LogInfoProduct
     * 
     * @param line
     *            日志行
     * @return
     */
    LogInfoProduct convertor(String line);

}
