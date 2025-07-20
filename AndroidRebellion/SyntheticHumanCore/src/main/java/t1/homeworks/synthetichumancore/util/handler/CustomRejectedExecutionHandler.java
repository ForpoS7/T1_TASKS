package t1.homeworks.synthetichumancore.util.handler;

import lombok.RequiredArgsConstructor;
import t1.homeworks.synthetichumancore.exception.QueueOverflowException;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@RequiredArgsConstructor
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    private final String androidName;

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new QueueOverflowException(
                String.format("The command queue for android '%s' is full. Unable to execute command: %s",
                        androidName, r.toString())
        );
    }
}
