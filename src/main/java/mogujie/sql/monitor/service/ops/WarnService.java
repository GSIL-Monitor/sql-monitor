package mogujie.sql.monitor.service.ops;

/**
 * 告警接口
 * 
 * @author luoqi luoqi@mogujie.com
 * @date 2014年3月3日 下午3:09:42
 */
public interface WarnService {

    /**
     * 短信告警接口
     * 
     * @param receivers
     *            接受者手机号
     * @param content
     *            内容
     */
    public void smsWarn(String receivers, String content);

    /**
     * 邮件告警接口
     * 
     * @param receivers
     *            接受者邮箱
     * @param subject
     *            邮件主题
     * @param content
     *            邮件内容
     */
    public void mailWarn(String receivers, String subject, String content);

}
