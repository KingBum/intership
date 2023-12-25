package example.springSecurity.controller;


import example.springSecurity.entity.UserRole;
import example.springSecurity.event.UserRegisteredEvent;
import example.springSecurity.repository.UserRepository;
import example.springSecurity.repository.UserRoleRepository;
import example.springSecurity.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import example.springSecurity.entity.User;
import example.springSecurity.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	public AuthController(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtService jwtService; 

	@Autowired
	private AuthenticationManager authenticationManager; 

	private boolean isTokenValid(LocalDateTime expirationTime) {
		// Check time still verify
		return expirationTime.isAfter(LocalDateTime.now());
	}

	private String generateVerificationToken() {
		return UUID.randomUUID().toString();
	}
	public void createVerifiedUser(User user) {
		// Create token
		String verificationToken = generateVerificationToken();

		// set time expired 5'
		LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

		// set token and time expired for user
		user.setVerificationToken(verificationToken);
		user.setExpirationTime(expirationTime);
		String urlAPI = "http://localhost:8080/api/auth/verify?token=";
		emailService.sendVerificationEmail(user.getUsername(),user.getEmail(),urlAPI+verificationToken );
	}


	@GetMapping("/test")
	public String testapi() {
        return "DONE";
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(null);
		}
		if (user.getRole() == null) {
			UserRole defaultRole = userRoleRepository.findByRoleName("ROLE_USER").orElse(null);
			user.setRole(defaultRole);
		}

		user.setPassword(encoder.encode(user.getPassword()));
		createVerifiedUser(user);
		User savedUser = userRepository.save(user);
		eventPublisher.publishEvent(new UserRegisteredEvent(user));
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@GetMapping("/verify?token={verificationToken}")
	public ResponseEntity<String> verifyAccount(@PathVariable String verificationToken) {
		User user = userRepository.findByVerificationToken(verificationToken);

		if (user != null && !user.isVerified() && isTokenValid(user.getExpirationTime())) {
			user.setVerified(true);
			user.setVerificationToken(null);
			user.setExpirationTime(null);
			userRepository.save(user);
			return ResponseEntity.ok("Account verified successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification token.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginAndGenerateToken(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			if (authentication.isAuthenticated()) {
				String token = jwtService.generateToken(user.getUsername());
				return ResponseEntity.ok(token);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Authentication failed");
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authentication failed: " + e.getMessage());
		}
	}

} 
