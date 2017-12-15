/**
 * 
 */
package mogujie.sql.monitor.entity.ops;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * Request : Header and Body
 * 
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class OpsProto {

    private String api;

    private List<NameValuePair> params;

    public OpsProto() {

    }

    /**
     * @param api
     * @param params
     */
    public OpsProto(String api, List<NameValuePair> params) {
        super();
        this.api = api;
        this.params = params;
    }

    /**
     * @return the api
     */
    public String getApi() {
        return api;
    }

    /**
     * @param api
     *            the api to set
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * @return the params
     */
    public List<NameValuePair> getParams() {
        return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

}
