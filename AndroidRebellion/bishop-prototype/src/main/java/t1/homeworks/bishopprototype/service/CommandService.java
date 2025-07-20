package t1.homeworks.bishopprototype.service;

import org.springframework.stereotype.Service;
import t1.homeworks.bishopprototype.exception.EntityNotFoundException;
import t1.homeworks.synthetichumancore.audit.WeylandWatchingYou;
import t1.homeworks.synthetichumancore.constant.Priority;
import t1.homeworks.synthetichumancore.entity.Android;
import t1.homeworks.synthetichumancore.entity.Command;

@Service
public class CommandService {
    private final AndroidService androidService;

    public CommandService(AndroidService androidService) {
        this.androidService = androidService;
    }

    @WeylandWatchingYou
    public void executeCommand(String androidName, Command command) {
        Android android = androidService.getAndroid(androidName);
        if (android == null) {
            throw new EntityNotFoundException("Android with name " + androidName + " not found.");
        }
        android.executeCommand(command);
    }

    public void testQueueOverflow(String androidName) {
        Android android = androidService.getAndroid(androidName);
        if (android == null) {
            throw new EntityNotFoundException("Android with name " + androidName + " not found.");
        }

        for (int i = 0; i < 30; i++) {
            Command command = new Command("Test Command " + i,
                    Priority.COMMON,
                    "TestAuthor",
                    "2025-07-19T14:30:00+03:00");
            android.executeCommand(command);
        }
    }
}
