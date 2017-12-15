package mogujie.sql.monitor.entity.product;

import java.util.Date;

/**
 * 解析后的日志信息对象
 * 
 * @author luoqi luoqi@mogujie.com
 * @date 2014年2月26日 上午10:58:20
 */
public class LogInfoProduct implements Product {

    private static final long serialVersionUID = 96343062871666756L;
    public static final String BEGIN = "beginTransaction";
    public static final String COMMIT = "commit";
    public static final String ROLLBACK = "rollBack";

    // Log原始数据 ===============================================================
    private Date executeTime = null;// sql执行时间
    private String hostName = "";// 主机名称
    private int pid = 0;// PID
    private long txId = -1L;// 事务Id
    private String dsn = "";// 请求的目标URL,无用暂时不解析
    private String action = "";// php自定义的SQL类型,无用暂时不解析
    private double cost = 0D;// sql消耗时间
    private String url = "";// 请求的action来自page request,无用暂时不解析
    private String sql = "";// 原始sql语句
    //
    private String schema = "";// schema即dbName
    private String tableName = "";// Sql中的表名
    // Log原始数据 end ============================================================
    private String formatSql = "";// 归一化后的SQL

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isInTransction() {
        return this.txId > 0;
    }

    public long getTxId() {
        return txId;
    }

    public void setTxId(long txId) {
        this.txId = txId;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDsn() {
        return dsn;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isBegin() {
        return sql.equals(BEGIN);
    }

    public boolean isCommit() {
        return sql.equals(COMMIT);
    }

    public boolean isRollback() {
        return sql.equals(ROLLBACK);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFormatSql() {
        return formatSql;
    }

    public void setFormatSql(String formatSql) {
        this.formatSql = formatSql;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the begin
     */
    public static String getBegin() {
        return BEGIN;
    }

    /**
     * @return the commit
     */
    public static String getCommit() {
        return COMMIT;
    }

    /**
     * @return the rollback
     */
    public static String getRollback() {
        return ROLLBACK;
    }
}
