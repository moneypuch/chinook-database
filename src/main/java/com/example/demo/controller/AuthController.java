package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.model.RefreshTokenRequest;
import com.example.demo.service.LoggingService;
import com.example.demo.service.PasswordService;
import com.example.demo.service.SecurityService;
import com.example.demo.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "AuthController", description = "API for user authentication and password management")
@RestController
@Slf4j
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final PasswordService passwordService;

    private final LoggingService loggingService;

    final
    SecurityService securityService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, PasswordService passwordService, LoggingService loggingService, SecurityService securityService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordService = passwordService;
        this.loggingService = loggingService;
        this.securityService = securityService;
    }

    @Operation(
            summary = "Authenticate user and generate JWT",
            description = "Authenticate user with their credentials and generate a JWT. Use the standard password: Jo5hu4!"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @Parameter(description = "User credentials", required = true) @RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            log.error(e.getMessage());
            loggingService.logActivity(authenticationRequest.getUsername(), "LOGIN_FAILURE");
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.createAccessToken(userDetails);
        boolean isManager = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_MANAGER"::equals);
        final String refreshToken = isManager ? jwtUtil.createRefreshToken(userDetails) : null;

        loggingService.logActivity(authenticationRequest.getUsername(), "LOGIN_SUCCESS");
        
        log.info("User {} successfully authenticated and JWT generated.", authenticationRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(token,refreshToken));
    }

    @Operation(summary = "Refresh JWT token")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "Refresh token", required = true) @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        try {
            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(401).body("Refresh token is expired. Please log in again.");
            }
            final String username = jwtUtil.extractUsername(refreshToken);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtil.generateNewAccessToken(refreshToken, userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid refresh token");
        }
    }

    @Operation(summary = "Change user password")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Parameter(description = "New password", required = true) @RequestParam String newPassword,
            Principal principal) {

        String username = principal.getName();

        try {
            boolean success = passwordService.changePassword(username, newPassword);
            if (!success) {
                return ResponseEntity.badRequest().body("Password does not meet criteria or matches recent passwords.");
            }
        } catch (Exception e) {
            loggingService.logActivity(username, "PASSWORD_CHANGE_FAILURE");
            return ResponseEntity.badRequest().body("Password change failed.");
        }

        loggingService.logActivity(username, "PASSWORD_CHANGE_SUCCESS");
        return ResponseEntity.ok("Password changed successfully.");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}