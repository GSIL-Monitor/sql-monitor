package mogujie.sql.monitor.dao.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class HttpConnectionPoolImplTest {
    private final Logger logger = LoggerFactory.getLogger(ClientRunnable.class);

    @Test
    public void testMulitThread() throws InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        final int size = 35;
        Thread[] threads = new Thread[size];

        // URIs to perform GETs on
        String[] urisToGet = new String[size];
        for (int i = 0; i < size; i++) {
            urisToGet[i] = "http://www.baidu.com/";
            urisToGet[++i] = "http://www.google.com/";
            urisToGet[++i] = "http://www.mogujie.com/";
            urisToGet[++i] = "http://www.domain4.com/";
            urisToGet[++i] = "http://www.domain5.com/";
        }

        CloseableHttpClient[] clients = new CloseableHttpClient[size];
        // create a thread for each URI
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpget = new HttpGet(urisToGet[i]);

            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
            clients[i] = httpClient;
            Runnable r = new ClientRunnable(httpClient, httpget);
            threads[i] = new Thread(r);

        }
        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
        // join the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

    }

    public class ClientRunnable implements Runnable {

        private final CloseableHttpClient client;
        private final HttpContext context;
        private final HttpGet get;

        public static final int DEFAULT_INNER_CONNECTION_REQUEST_TIMEOUT = 3 * 1000;
        public static final int DEFAULT_INNER_CONECT_TIMEOUT = 60 * 1000;
        public static final int DEFAULT_INNER_SOCKET_TIMEOUT = 10 * 1000;

        /**
         * @param client
         * @param get
         */
        public ClientRunnable(CloseableHttpClient client, HttpGet get) {
            super();
            this.client = client;
            this.context = HttpClientContext.create();
            this.get = get;

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(DEFAULT_INNER_CONNECTION_REQUEST_TIMEOUT)
                    .setConnectTimeout(DEFAULT_INNER_CONECT_TIMEOUT).setSocketTimeout(DEFAULT_INNER_SOCKET_TIMEOUT)
                    .build();
            this.get.setConfig(requestConfig);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            try {
                CloseableHttpResponse response = client.execute(get, context);
                try {
                    HttpEntity entity = response.getEntity();
                    if (null != entity) {
                        logger.debug("response content body:{}", EntityUtils.toString(entity));
                    }
                } finally {
                    response.close();
                }
            } catch (Exception e) {

            }
        }
    }

}
