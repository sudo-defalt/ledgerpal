package com.snappay.ledgerpal.security.jwt.rest;

import com.snappay.ledgerpal.model.UserModel;
import com.snappay.ledgerpal.model.operation.UserRegistrationModel;
import com.snappay.ledgerpal.security.jwt.JwtCredential;
import com.snappay.ledgerpal.security.jwt.JwtCredentialService;
import com.snappay.ledgerpal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtCredentialService jwtCredentialService;

    @PostMapping("/signup")
    public ResponseEntity<UserModel> register(@RequestBody UserRegistrationModel model) {
        UserModel userModel = userService.register(model);
        return ResponseEntity.ok(userModel);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtCredential> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        JwtCredential jwtCredential = jwtCredentialService.generate(user);
        return ResponseEntity.ok(jwtCredential);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        if (jwtCredentialService.validate(request.refreshToken())) {
            UserDetails user = jwtCredentialService.extractUser(request.refreshToken());
            JwtCredential jwtCredential = jwtCredentialService.generate(user);
            return ResponseEntity.ok(jwtCredential);
        }
        return ResponseEntity.status(401).body("Invalid or expired refresh token");
    }


}
