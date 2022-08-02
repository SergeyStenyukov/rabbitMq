package example.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final String DEFAULT_DIRECT = "default_direct";

    private final String FIRST_KEY = "first";

    private final String SECOND_KEY = "second";

    @RabbitListener(containerFactory = "defaultQueue", queues = {DEFAULT_DIRECT})
    public void readMessage(Message message){
        System.out.println("MESSAGE READ from " + DEFAULT_DIRECT + ", message: " + message);
    }

    @RabbitListener(containerFactory = "directExchange", bindings = @QueueBinding(exchange = @Exchange("direct_exchange"),
            key = FIRST_KEY, value = @Queue("")))
    public void readMessageFromDirectFirst(Message message){
        System.out.println("MESSAGE READ from direct exchange, key first, message: " + message);
    }

    @RabbitListener(containerFactory = "directExchange", bindings = @QueueBinding(exchange = @Exchange("direct_exchange"),
            key = SECOND_KEY, value = @Queue("")))
    public void readMessageFromDirectSecond(Message message){
        System.out.println("MESSAGE READ from direct exchange, key second, message: " + message);
    }
}
