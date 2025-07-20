package t1.homeworks.bishopprototype.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import t1.homeworks.synthetichumancore.entity.Android;
import t1.homeworks.synthetichumancore.metric.AndroidMetrics;
import t1.homeworks.synthetichumancore.util.handler.GlobalExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class SyntheticHumanConfig {
    @Bean
    public Map<String, Android> androids(AndroidMetrics androidMetrics) {
        Map<String, Android> androids = new HashMap<>();

        androids.put("Конор", new Android("Конор", androidMetrics));
        androids.put("Маркус", new Android("Маркус", androidMetrics,
                2, 6, 5,
                TimeUnit.SECONDS, 15));
        androids.put("Кэра", new Android("Кэра", androidMetrics));

        return androids;
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
