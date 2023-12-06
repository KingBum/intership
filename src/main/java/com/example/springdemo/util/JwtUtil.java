//package com.example.springdemo.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtUtil {
//    private final SecretKey SECRET = generateSecretKey();
//
//    private SecretKey generateSecretKey() {
//        // You can use any method to generate your SecretKey
//        // For example, using KeyGenerator
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance(SignatureAlgorithm.HS256.getJcaName());
//            // You can specify the key size as needed
//            // keyGenerator.init(256);
//            return keyGenerator.generateKey();
//        } catch (NoSuchAlgorithmException e) {
//            // Handle the exception appropriately
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    private final long EXPIRATION_TIME = 900_000; // 15 minutes
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().verifyWith(SECRET).deserializeJsonWith(token).getBody();
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails);
//    }
//
//    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(String.valueOf(userDetails))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS256, SECRET)
//                .compact();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    // Additional utility methods...
//}