/**
 * 
 */
package mogujie.sql.monitor.service.ops;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import mogujie.sql.monitor.dao.HttpConnectionPool;
import mogujie.sql.monitor.entity.ops.OpsProto;
import mogujie.sql.monitor.entity.ops.OpsResponse;
import mogujie.sql.monitor.util.IOUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Service
public class OpsServiceImpl implements OpsService {
    private static final Logger logger = LoggerFactory.getLogger(OpsServiceImpl.class);

    @Resource
    private HttpConnectionPool http;

    private final ObjectMapper objectMapper = new ObjectMapper();;

    private AtomicLong success = new AtomicLong(0);
    private AtomicLong failure = new AtomicLong(0);

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.ops.OpsService#successCount()
     */
    @Override
    public long successCount() {
        return success.get();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.service.ops.OpsService#failCount()
     */
    @Override
    public long failCount() {
        return failure.get();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.service.ops.OpsService#send(mogujie.sql.monitor.entity
     * .ops.OpsProto)
     */
    @Override
    public boolean send(OpsProto opsProto) {
        if (null == opsProto) {
            return true;
        }

        CloseableHttpResponse response = null;
        URI uri = null;
        try {
            final URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setPath(opsProto.getApi());
            uriBuilder.addParameters(opsProto.getParams());
            uri = uriBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = http.execute(httpGet);
            uriBuilder.clearParameters();

            boolean doSuccess = false;
            // not success , to record the log
            if (null != response) {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getStatusCode() >= 200 || response.getStatusLine().getStatusCode() < 300) {
                    InputStream is = entity.getContent();
                    OpsResponse opsResponse = null;
                    try {
                        opsResponse = objectMapper.readValue(is, OpsResponse.class);
                    } catch (Exception jsonEx) {
                        logger.error("Exception", jsonEx);
                        is.reset();
                        String responseStr = EntityUtils.toString(entity);
                        logger.error("Response: {}", responseStr);
                    } finally {
                        IOUtil.close(is);
                    }
                    if (null != opsResponse && opsResponse.getStatus() == 1001) {
                        success.incrementAndGet();
                        doSuccess = true;
                        return true;
                    }
                } else {
                    logger.error("{} {}", response, uri);
                }
                // handle http response end.
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            IOUtil.close(response);
        }
        long f = failure.incrementAndGet();
        logger.error("Ops do not handle successfully, total times: {}. Now occurs {} .", Long.valueOf(f).toString(),
                uri);
        return false;
    }
}
