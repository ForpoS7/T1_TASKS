package t1.homeworks.synthetichumancore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import t1.homeworks.synthetichumancore.audit.AuditProperties;
import t1.homeworks.synthetichumancore.audit.WeylandAuditAspect;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
@EnableAspectJAutoProxy
public class AuditAutoConfiguration {
    @Bean
    public AuditProperties auditProperties() {
        return new AuditProperties();
    }
    @Bean
    public WeylandAuditAspect weylandAuditAspect(AuditProperties auditProperties) {
        return new WeylandAuditAspect(auditProperties);
    }
}
