package t1.homeworks.bishopprototype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t1.homeworks.bishopprototype.exception.EntityNotFoundException;
import t1.homeworks.synthetichumancore.entity.Android;

import java.util.Map;

@Service
public class AndroidService {
    private final Map<String, Android> androids;

    public AndroidService(@Autowired Map<String, Android> androids) {
        this.androids = androids;
    }

    public Android getAndroid(String name) {
        return androids.get(name);
    }

    public Integer getExecutedTasks(String name) {
        Android android = androids.get(name);
        if (android == null) {
            throw new EntityNotFoundException("Android with name " + name + " not found.");
        }
        return android.getExecutedTasks().get();
    }

    public String getStatus(String name) {
        Android android = androids.get(name);
        if (android == null) {
            throw new EntityNotFoundException("Android with name " + name + " not found.");
        }
        return android.isShutdown() ? "Shutdown" : "Active";
    }

    public String shutdown(String name) {
        Android android = androids.get(name);
        if (android == null) {
            throw new EntityNotFoundException("Android with name " + name + " not found.");
        }
        android.shutdown();
        androids.remove(name);
        return name;
    }
}
