package t1.homeworks.synthetichumancore.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import t1.homeworks.synthetichumancore.constant.AuditMode;

@Getter
@Setter
@ConfigurationProperties(prefix = "audit")
public class AuditProperties {
    private AuditMode mode = AuditMode.CONSOLE;
    private String kafkaTopic = "audit-topic";
    private String kafkaBootstrapServers = "localhost:9092";
}
