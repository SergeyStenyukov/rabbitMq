package example.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final String DIRECT = "direct";

    @RabbitListener(queues = {DIRECT})
    public void readMessage(Message message){
        System.out.println("MESSAGE READ from " + DIRECT + ", message: " + message);
    }
}
