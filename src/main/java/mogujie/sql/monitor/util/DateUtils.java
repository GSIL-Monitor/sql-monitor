package mogujie.sql.monitor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String FORMATE_TIME_TO_MINUTE_PATTERN = "yyyyMMddHHmm";

    public static Long formatToMinute(Date executeTime) {
        final SimpleDateFormat sdf = new SimpleDateFormat(FORMATE_TIME_TO_MINUTE_PATTERN);
        String formatted = sdf.format(executeTime);
        Long et = Long.valueOf(formatted);
        return et;
    }

    /**
     * @param minute
     * @return Unix TimeStamp
     * @throws ParseException
     */
    public static long getTimestampFromMinute(String minute) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(FORMATE_TIME_TO_MINUTE_PATTERN);
        Date minu = sdf.parse(minute);
        long timestamp = minu.getTime() / 1000;
        return timestamp;
    }

}
