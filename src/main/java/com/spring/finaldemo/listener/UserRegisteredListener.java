package com.spring.finaldemo.listener;
import com.spring.finaldemo.entity.User;
import com.spring.finaldemo.event.UserRegisteredEvent;
import com.spring.finaldemo.repository.UserRepository;
import com.spring.finaldemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class UserRegisteredListener {

    private final EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Autowired
    public UserRegisteredListener(EmailService emailService) {
        this.emailService = emailService;
    }

    public static boolean isExpired(LocalDateTime expirationTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expirationTime);
    }
    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        User user = (User) event.getSource();

        executorService.schedule(() -> {
            User currentState = userRepository.findByUsername(user.getUsername()).get();
            boolean isExpired = isExpired(user.getExpirationTime());

            if (currentState.isVerified() && isExpired) {
                System.out.println("User " + user.getUsername() + " is verified success.");
            } else {
                userRepository.delete(user);
                emailService.sendNotificationEmail(user.getUsername(), user.getEmail(), "The authentication time has expired and your account will be deleted!");
                System.out.println("User " + user.getUsername() + " is not verified");
            }
        }, 6, TimeUnit.MINUTES);

    }
}
