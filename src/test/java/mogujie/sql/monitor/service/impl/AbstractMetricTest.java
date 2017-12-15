package mogujie.sql.monitor.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mogujie.sql.monitor.service.AbstractMetric;

import org.testng.annotations.Test;

public class AbstractMetricTest {
    @Test
    public void formatExecuteTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

        Date n = sdf.parse("20140911 13:23:00");
        AbstractMetric metric = new TransactionMetric();
        Long actual = metric.formatExecuteTime(n);

        assert actual.equals(Long.valueOf("201409111323"));
    }
}
