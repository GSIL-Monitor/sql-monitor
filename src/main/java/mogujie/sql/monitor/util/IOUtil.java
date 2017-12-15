package mogujie.sql.monitor.util;

import java.io.Closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public final static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (null != closeable) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    logger.error("Exception:", e);
                }
            }
        }

    }
}
