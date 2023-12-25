package example.springSecurity.service;
import example.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(String username, String to, String verificationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Verification " + username);
        message.setText("Please click the following link to verify your account: " + verificationToken);
        javaMailSender.send(message);
    }

//    @Scheduled(fixedDelay = 30000) //
//    public void sendExpirationNotification() {
//        List<User> expiredUsers = userRepository.findByIsVerifiedFalseAndExpirationTimeBefore(LocalDateTime.now());
//
//        for (User user : expiredUsers) {
//            sendNotificationEmail(user.getUsername(), user.getEmail(), "Your verification has expired.");
//        }
//    }

    public void sendNotificationEmail(String username, String to, String notificationMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verification Expiration " + username);
        message.setText(notificationMessage);
        javaMailSender.send(message);
    }
}
