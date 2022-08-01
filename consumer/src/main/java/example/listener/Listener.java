package example.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final String DEFAULT_DIRECT = "default_direct";

    private final String FIRST_DIRECT = "first_direct";

    private final String SECOND_DIRECT = "second_direct";

    @RabbitListener(containerFactory = "defaultQueue", queues = {DEFAULT_DIRECT})
    public void readMessage(Message message){
        System.out.println("MESSAGE READ from " + DEFAULT_DIRECT + ", message: " + message);
    }

    @RabbitListener(containerFactory = "directExchange", queues = {FIRST_DIRECT, SECOND_DIRECT})
    public void readMessageFromDirect(Message message){
        System.out.println("MESSAGE READ from direct exchange, message: " + message);
    }
}
