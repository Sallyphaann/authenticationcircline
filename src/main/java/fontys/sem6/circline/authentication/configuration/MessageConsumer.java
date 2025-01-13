package fontys.sem6.circline.authentication.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.sem6.circline.authentication.persistence.AccountRepository;
import fontys.sem6.circline.authentication.persistence.entity.AccountEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageConsumer {
    private final Set<Long> processedIds = ConcurrentHashMap.newKeySet();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @RabbitListener(queues = "hello-queue")
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(queues = "auth-queue")
    public void processMessage(String message) {

        System.out.println("Received message: " + message);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
            String userId = (String) messageMap.get("userId");
            String email = (String) messageMap.get("email");
            String password = (String) messageMap.get("password");
            String role = (String) messageMap.get("role");
            Optional<AccountEntity> existingAccount = Optional.ofNullable(accountRepository.findByEmail(email));
            if (existingAccount.isPresent()) {
                System.out.println("Account already exists for email: " + email);
                return;
            }
            AccountEntity newAccount = AccountEntity.builder().userId(Long.parseLong(userId))
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role).build();
            accountRepository.save(newAccount);
            System.out.println("Processed userId: " + userId + ", email: " + email);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing the message: " + e.getMessage());
        }
    }
    @RabbitListener(queues = "rm-queue")
    public void getMessageToRm(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Long userId = objectMapper.readValue(message, Long.class);
            AccountEntity account = accountRepository.findByUserId(userId);
            if (processedIds.contains(userId)) {
                System.out.println("Duplicate message detected for userId: " + userId);
                return;
            }
            processedIds.add(userId);
            if (account != null) {
                accountRepository.delete(account);
                System.out.println("Account removed successfully for userId: " + userId);
            } else {
                System.out.println("No account found for userId: " + userId);
            }

        } catch (JsonProcessingException e) {
            System.out.println("Error parsing the message: " + e.getMessage());
        }
    }



}
