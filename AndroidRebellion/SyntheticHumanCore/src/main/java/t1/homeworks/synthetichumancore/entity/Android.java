package t1.homeworks.synthetichumancore.entity;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import t1.homeworks.synthetichumancore.constant.Priority;
import t1.homeworks.synthetichumancore.metric.AndroidMetrics;
import t1.homeworks.synthetichumancore.util.handler.CustomRejectedExecutionHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Android {
    private static final Logger logger = LoggerFactory.getLogger(Android.class);
    private final AndroidMetrics androidMetrics;

    private final ThreadPoolExecutor executor;
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicInteger executedTasks = new AtomicInteger(0);

    private final String name;

    public Android(String name, AndroidMetrics androidMetrics) {
        this(name, androidMetrics, 1, 4, 10, TimeUnit.SECONDS, 10);
    }

    public Android(String name, AndroidMetrics androidMetrics,
                   int corePoolSize, int maximumPoolSize,
                   long keepAliveTime, TimeUnit unit,
                   Integer queueCapacity) {
        this.name = name;
        this.androidMetrics = androidMetrics;
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity);
        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                taskQueue,
                new CustomRejectedExecutionHandler(name)
        );
        androidMetrics.registerAndroid(this);
    }

    public void executeCommand(Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            executeCriticalCommand(command);
        } else {
            executor.execute(() -> processCommand(command));
            androidMetrics.updateQueueSize(name, executor.getQueue().size());
        }
    }

    private void processCommand(Command command) {
        logger.info("{} execute the command: {}", name, command.toString());
        androidMetrics.incrementExecutedTasks(command.getAuthor());
        executedTasks.incrementAndGet();
    }

    private void executeCriticalCommand(Command command) {
        new Thread(() -> processCommand(command)).start();
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        logger.info("Android {} has finished its work. Total commands executed: {}", name, executedTasks.get());
    }

    public boolean isShutdown() {
        return executor.isShutdown();
    }
}
