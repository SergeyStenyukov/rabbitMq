package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitConsumer {

    public static void main(String[] args) {
        SpringApplication.run(RabbitConsumer.class, args);
    }
}
