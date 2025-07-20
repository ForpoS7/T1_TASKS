package t1.homeworks.synthetichumancore.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import t1.homeworks.synthetichumancore.entity.Android;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class AndroidMetrics {
    private final MeterRegistry meterRegistry;

    private final Map<String, Integer> taskQueueSize = new ConcurrentHashMap<>();

    private final Map<String, Counter> executedTasksCounters = new ConcurrentHashMap<>();

    public AndroidMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void registerAndroid(Android android) {
        String androidName = android.getName();
        BlockingQueue<Runnable> taskQueue = android.getTaskQueue();

        registerTaskQueueMetric(androidName, taskQueue);
        updateQueueSize(androidName, taskQueue.size());
    }

    public void updateQueueSize(String androidName, int size) {
        taskQueueSize.put(androidName, size);
    }

    public void incrementExecutedTasks(String author) {
        executedTasksCounters.computeIfAbsent(author, key -> Counter.builder("android.executed.tasks")
                        .tag("author", key)
                        .description("Number of tasks executed by the android for a specific author")
                        .register(meterRegistry))
                .increment();
    }

    private void registerTaskQueueMetric(String androidName, BlockingQueue<Runnable> taskQueue) {
        Gauge.builder("android.task.queue.size", taskQueue::size)
                .tag("android", androidName)
                .description("Current number of tasks in the queue for android: " + androidName)
                .register(meterRegistry);
    }
}
