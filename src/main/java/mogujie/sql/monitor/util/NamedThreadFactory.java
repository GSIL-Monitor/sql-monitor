package mogujie.sql.monitor.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NamedThreadFactory implements ThreadFactory {

    private static final Logger logger = LoggerFactory.getLogger(NamedThreadFactory.class);

    private final ThreadGroup group;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    final static UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            logger.error("from " + t.getName(), e);
        }
    };

    public NamedThreadFactory(String poolName) {
        if (null == poolName || poolName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        group = Thread.currentThread().getThreadGroup();
        namePrefix = poolName + "-Pool-Thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        return t;
    }
}
