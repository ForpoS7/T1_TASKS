package t1.homeworks.synthetichumancore.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;

@Aspect
public class WeylandAuditAspect {
    private static final Logger logger = LoggerFactory.getLogger(WeylandAuditAspect.class);

    private final AuditProperties auditProperties;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public WeylandAuditAspect(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    @Around("@annotation(WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder auditMessageBuilder = new StringBuilder();
        auditMessageBuilder.append("Class: ")
                .append(className)
                .append(" Method: ")
                .append(methodName)
                .append(" called with arguments: ")
                .append(Arrays.toString(args));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            auditMessageBuilder.append(" Error: ")
                    .append(throwable.getMessage());
            sendAuditLog(auditMessageBuilder.toString());
            throw throwable;
        }

        auditMessageBuilder.append(" Result: ")
                .append(result);
        sendAuditLog(auditMessageBuilder.toString());

        return result;
    }

    private void sendAuditLog(String message) {
        switch (auditProperties.getMode()) {
            case KAFKA:
                if (kafkaTemplate != null) {
                    kafkaTemplate.send(auditProperties.getKafkaTopic(), message);
                    logger.info("Audit log sent to Kafka topic: {}", auditProperties.getKafkaTopic());
                } else {
                    logger.warn("KafkaTemplate is not configured! Audit log cannot be sent to Kafka.");
                }
                break;
            case CONSOLE:
                logger.info("Audit log printed to console: {}", message);
                break;
            default:
                logger.warn("Unknown audit mode: {}", auditProperties.getMode());
        }
    }

    @Autowired(required = false)
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
