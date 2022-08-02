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

    @RabbitListener(containerFactory = "containerFactory", queues = {DEFAULT_DIRECT})
    public void readMessage(Message message){
        System.out.println("MESSAGE READ from " + DEFAULT_DIRECT + ", message: " + message);
    }

    @RabbitListener(containerFactory = "containerFactory", bindings = @QueueBinding(exchange = @Exchange("direct_exchange"),
            key = FIRST_KEY, value = @Queue("")))
    public void readMessageFromDirectFirst(Message message){
        System.out.println("MESSAGE READ from direct exchange, key first, message: " + message);
    }

    @RabbitListener(containerFactory = "containerFactory", bindings = @QueueBinding(exchange = @Exchange("direct_exchange"),
            key = SECOND_KEY, value = @Queue("")))
    public void readMessageFromDirectSecond(Message message){
        System.out.println("MESSAGE READ from direct exchange, key second, message: " + message);
    }

    @RabbitListener(containerFactory = "containerFactory", queues = {"first_fanout", "second_fanout"})
    public void readMessageFromFanout(Message message){
        System.out.println("MESSAGE READ from fanout exchange, message: " + message);
    }

    @RabbitListener(containerFactory = "containerFactory", queues = {"first_topic", "second_topic", "general_topic"})
    public void readMessageFromTopic(Message message){
        System.out.println("MESSAGE READ from topic exchange, message: " + message);
    }

    @RabbitListener(containerFactory = "containerFactory", queues = {"first_header", "second_header"})
    public void readMessageFromHeader(Message message){
        System.out.println("MESSAGE READ from header exchange, message: " + message);
    }
}
