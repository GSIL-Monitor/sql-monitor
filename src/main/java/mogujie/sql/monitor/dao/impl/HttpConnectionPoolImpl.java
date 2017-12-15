/**
 * 
 */
package mogujie.sql.monitor.dao.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import mogujie.sql.monitor.config.AppConfig;
import mogujie.sql.monitor.dao.HttpConnectionPool;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
@Repository
public class HttpConnectionPoolImpl implements HttpConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionPoolImpl.class);

    public static final int DEFAULT_INNER_CONNECTION_REQUEST_TIMEOUT = 50;
    public static final int DEFAULT_INNER_CONECT_TIMEOUT = 60 * 1000;
    public static final int DEFAULT_INNER_SOCKET_TIMEOUT = 10 * 1000;

    public static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(DEFAULT_INNER_CONNECTION_REQUEST_TIMEOUT)
            .setConnectTimeout(DEFAULT_INNER_CONECT_TIMEOUT).setSocketTimeout(DEFAULT_INNER_SOCKET_TIMEOUT).build();

    private HttpHost server = null;
    private PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    @Resource
    private AppConfig appConfig = null;

    private String hostname = null;
    private int port = 0;

    @Override
    @PostConstruct
    public void init() {
        final int maxPerRouter = 20;
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        server = new HttpHost(hostname, port);
        HttpRoute route = new HttpRoute(server);
        cm.setMaxPerRoute(route, maxPerRouter);
    }

    @Override
    @Autowired(required = true)
    public void setHost(@Value("${ops.hostname}") String hostname, @Value("${ops.port}") int port) {
        Assert.isTrue(!hostname.isEmpty());
        Assert.isTrue(port > 0);
        this.hostname = hostname;
        this.port = port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mogujie.sql.monitor.dao.HttpConnectionPool#getCloseableHttpClient()
     */
    @Override
    public CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        return httpClient;
    }

    @Override
    @PreDestroy
    public void destory() {
        cm.shutdown();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.dao.HttpConnectionPool#execute(org.apache.http.client
     * .methods.HttpGet)
     */
    @Override
    public CloseableHttpResponse execute(HttpGet httpGet) throws ClientProtocolException, IOException {
        return getCloseableHttpClient().execute(server, httpGet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mogujie.sql.monitor.dao.HttpConnectionPool#execute(org.apache.http.client
     * .methods.HttpPost)
     */
    @Override
    public CloseableHttpResponse execute(HttpPost httpPost) throws ClientProtocolException, IOException {
        return getCloseableHttpClient().execute(server, httpPost);
    }

}
