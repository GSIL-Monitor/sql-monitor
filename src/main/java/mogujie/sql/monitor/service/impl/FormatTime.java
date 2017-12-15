/**
 * 
 */
package mogujie.sql.monitor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public final class FormatTime {
    private static final Logger logger = LoggerFactory.getLogger(FormatTime.class);

    public static final String FORMATE_TIME_KEY_PATTERN = "yyyyMMddHHmm";

    public static Long formatExecuteTime(Date executeTime) {
        final SimpleDateFormat sdf = new SimpleDateFormat(FORMATE_TIME_KEY_PATTERN);
        String formatted = sdf.format(executeTime);
        Long et = Long.valueOf(formatted);
        return et;
    }
}
