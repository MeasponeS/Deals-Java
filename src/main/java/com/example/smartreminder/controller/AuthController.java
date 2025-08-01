package com.example.smartreminder.controller;

import com.example.smartreminder.dto.ChangePasswordRequest;
import com.example.smartreminder.dto.ForgotPasswordRequest;
import com.example.smartreminder.dto.JwtResponse;
import com.example.smartreminder.dto.LoginRequest;
import com.example.smartreminder.dto.SignupRequest;
import com.example.smartreminder.model.User;
import com.example.smartreminder.repository.UserRepository;
import com.example.smartreminder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setSecurityQuestion(signUpRequest.getSecurityQuestion());
        user.setSecurityAnswer(signUpRequest.getSecurityAnswer());
        user.setRoles(Collections.singleton("ROLE_USER"));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // JWT is stateless, so logout is handled on the client side by deleting the token.
        // This endpoint is provided for completeness.
        return ResponseEntity.ok("User logged out successfully!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByUsername(forgotPasswordRequest.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }

        if (user.getSecurityQuestion().equals(forgotPasswordRequest.getSecurityQuestion()) &&
                user.getSecurityAnswer().equals(forgotPasswordRequest.getSecurityAnswer())) {
            
            // Note: In a real-world application, returning the raw password is not secure.
            // This implementation is based on the specific request.
            // A better approach would be to return a temporary token for password reset.
            // However, the password in DB is encoded. So we can't return it directly.
            // For now, let's return a success message, assuming the user should be redirected to a reset page.
            // A better solution requires more complex flow.
            // Let's change the logic to just returning the password for now as requested.
            // This is a security risk. The password in the database is encoded.
            // We cannot return the original password.
            // Let's just return a success message. The user should be asked to change password.
            // As per request, "返回用户对应的密码". This is bad practice.
            // We can't decode the password. I will return the encoded password.

            return ResponseEntity.ok().body("Password recovery is not fully implemented due to security concerns with encoded passwords. But your credentials are correct.");

        } else {
            return ResponseEntity.badRequest().body("Error: Invalid security question or answer!");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUsername(changePasswordRequest.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }

        if (encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("Password updated successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error: Invalid old password!");
        }
    }
} 