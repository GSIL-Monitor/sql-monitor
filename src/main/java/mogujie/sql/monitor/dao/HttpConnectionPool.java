/**
 * 
 */
package mogujie.sql.monitor.dao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public interface HttpConnectionPool {

    void setHost(String host, int port);

    /**
     * initial configuration
     */
    void init();

    CloseableHttpClient getCloseableHttpClient();

    void destory();

    /**
     * @param httpGet
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    CloseableHttpResponse execute(HttpGet httpGet) throws ClientProtocolException,
            IOException;

    /**
     * @param httpPost
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    CloseableHttpResponse execute(HttpPost httpPost) throws ClientProtocolException, IOException;

}
