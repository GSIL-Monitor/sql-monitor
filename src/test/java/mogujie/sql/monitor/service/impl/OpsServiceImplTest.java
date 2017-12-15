package mogujie.sql.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.service.ops.OpsService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath:spring/context.xml" })
public class OpsServiceImplTest extends AbstractTestNGSpringContextTests {

    @BeforeClass
    public void beforeClass() {
    }

    @Test
    public void testSend() {
        OpsService opsService = super.applicationContext.getBean(OpsService.class);
        OpsProto request = new OpsProto();
        String api = "/upload.php";
        request.setApi(api);
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair e = null;

        e = new BasicNameValuePair("key", "SQL-MONITOR_TradeItemCheck_qps_avg");
        params.add(e);

        e = new BasicNameValuePair("val", "36.9");
        params.add(e);

        e = new BasicNameValuePair("type", "3");
        params.add(e);

        long timestamp = System.currentTimeMillis() / 1000;
        e = new BasicNameValuePair("time", String.valueOf(timestamp));
        params.add(e);

        request.setParams(params);
        boolean result = opsService.send(request);
        assert result;
    }
}
