package t1.homeworks.bishopprototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class BishopPrototypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BishopPrototypeApplication.class, args);
    }

}
