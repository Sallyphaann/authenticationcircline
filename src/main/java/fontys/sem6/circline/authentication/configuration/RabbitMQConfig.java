package fontys.sem6.circline.authentication.configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitmqHost != null ? rabbitmqHost : "localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
    @Bean
    public Queue helloQueue() {
        return new Queue("hello-queue", true); }
    @Bean
    public Queue authQueue() {
        return new Queue("auth-queue", true);
    }
    @Bean
    public Queue rmAccountQueue() {
        return new Queue("rm-queue", true);
    }
}
