package t1.homeworks.synthetichumancore.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import t1.homeworks.synthetichumancore.metric.AndroidMetrics;

@Configuration
public class MetricAutoConfig {
    @Bean
    public AndroidMetrics androidMetrics(MeterRegistry meterRegistry) {
        return new AndroidMetrics(meterRegistry);
    }
}
