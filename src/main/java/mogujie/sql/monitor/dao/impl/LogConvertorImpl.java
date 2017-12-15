package mogujie.sql.monitor.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mogujie.sql.monitor.dao.LogConvertor;
import mogujie.sql.monitor.entity.product.LogInfoProduct;
import mogujie.sql.monitor.util.SqlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LogConvertorImpl implements LogConvertor {
    private static final Logger logger = LoggerFactory.getLogger(LogConvertorImpl.class);

    private static final Logger errorLineLogger = LoggerFactory.getLogger("error.line");// 格式错误的行

    final Pattern REPLACE_NUMBER_PATTERN = Pattern.compile("((?<keep>[^a-zA-Z_0-9]*)\\d+)|(?<keep2>\\w+\\d*\\w*])");

    @Override
    public LogInfoProduct convertor(final String line) {
        if (null == line) {
            return null;
        }
        String trimLog = line.trim();
        if (trimLog.isEmpty()) {
            return null;
        }
        if (trimLog.length() < 20) {
            errorLineLogger.error("Invalid log: {}", trimLog);
            return null;
        }
        String strDt = trimLog.substring(0, 19);
        Date executeTime = null;
        try {
            executeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDt);
        } catch (ParseException e) {
            errorLineLogger.error("时间格式错误, log: {}, strDt: {}", trimLog, strDt);
            return null;
        }

        trimLog = trimLog.substring(20, trimLog.length() - 3);
        String[] array = trimLog.split(">>>");
        if (array.length != 10) {
            errorLineLogger.error("格式错误, original log: {}", line);
            return null;
        }

        String hostName = null;
        int pid = 0;
        long txId = 0;
        //
        double timestamp = 0L;
        String dbName = null;
        String dsn = null;
        String action = null;
        double cost = 0;
        String url = null;
        String sql = null;
        String tableName = null;
        LogInfoProduct ret = null;
        try {
            hostName = array[0].substring(array[0].indexOf(":") + 1, array[0].length());
            pid = Integer.parseInt(array[1].substring(array[1].indexOf(":") + 1, array[1].length()));
            txId = Long.parseLong(array[2].substring(array[2].indexOf(":") + 1, array[2].length()));
            timestamp = Double.parseDouble(array[3].substring(array[3].indexOf(":") + 1, array[3].length()));
            dbName = array[4].substring(array[4].indexOf(":") + 1, array[4].length());
            dsn = array[5].substring(array[5].indexOf(":") + 1, array[5].length());
            action = array[6].substring(array[6].indexOf(":") + 1, array[6].length());
            cost = Double.parseDouble(array[7].substring(array[7].indexOf(":") + 1, array[7].length()));
            url = array[8].substring(array[8].indexOf(":") + 1, array[8].length());
            sql = array[9].substring(array[9].indexOf(":") + 1, array[9].length());
            ret = new LogInfoProduct();
            ret.setExecuteTime(executeTime);
            ret.setHostName(hostName);
            ret.setPid(pid);
            ret.setTxId(txId);
            ret.setSchema(dbName);
            ret.setDsn(dsn);
            ret.setAction(action);
            ret.setCost(cost);
            ret.setUrl(url);
            ret.setSql(sql);
            if (!ret.isCommit() && !ret.isRollback() && !ret.isBegin()) {
                final String formatSql = trimSql2Unit(sql);
                tableName = SqlUtil.getTableName(sql);
                ret.setFormatSql(formatSql);
                ret.setTableName(tableName);
            } else {// commit或者rollback或者begin语句
                ret.setFormatSql(sql);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            errorLineLogger.error("格式错误, log: {}", trimLog);
            return null;
        }
        logger.debug(
                "hostName->{}, pid->{}, txId->{}, daName->{}, dsn->{}, action->{}, cost->{}, url->{}, sql->{}, tableName->{}",
                hostName, pid, txId, dbName, dsn, action, cost, url, sql, tableName);
        return ret;
    }

    private String trimSql2Unit(String sql) {
        String ret = sql.trim();
        // ret = new String(ret.getBytes("UTF-8"), "GBK");
        if (ret.startsWith("INSERT")) {
            int beginOfVal = ret.indexOf("VALUES");
            if (beginOfVal < 0) {
                beginOfVal = ret.indexOf("VALUE");
                if (!(beginOfVal < 0)) {
                    beginOfVal += 5;
                }
            } else {
                beginOfVal += 6;
            }
            if (beginOfVal > 0) {
                ret = ret.substring(0, beginOfVal);
            }
        } else if (ret.startsWith("insert")) {
            int beginOfVal = ret.indexOf("values");
            if (beginOfVal < 0) {
                beginOfVal = ret.indexOf("value");
                if (!(beginOfVal < 0)) {
                    beginOfVal += 5;
                }
            } else {
                beginOfVal += 6;
            }
            if (beginOfVal > 0) {
                ret = ret.substring(0, beginOfVal);
            }
        }
        ret = ret.replaceAll("\\( *\\?.*\\)", "(?)");
        ret = ret.replaceAll(" *'[^']*?'", "?");
        ret = ret.replaceAll(" *\"[^\"]*?\"", "?");
        if (ret.startsWith("UPDATE") || ret.startsWith("update")) {
            ret = ret.replaceAll("=\\?[^`=]*'", "=?");
        }
        ret = ret.replaceAll("(?i)in *\\(.+?\\)", "in (?)");
        ret = ret.replaceAll(" {2,}\\?", " ?");
        ret = this.replaceNumber4Value(ret);
        ret = ret.replaceAll("\\?(,\\?)+", "?");
        ret = ret.trim();
        return ret;
    }

    public String replaceNumber4Value(final String s) {
        Matcher m = REPLACE_NUMBER_PATTERN.matcher(s);
        final StringBuffer sb = new StringBuffer(150);
        while (m.find()) {
            String group = m.group("keep");
            if (group != null && !group.isEmpty()) {
                m.appendReplacement(sb, group + "?");
            }

            String group2 = m.group("keep2");
            if (group2 != null && !group2.isEmpty()) {
                m.appendReplacement(sb, group2);
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
