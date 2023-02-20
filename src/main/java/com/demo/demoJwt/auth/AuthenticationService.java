package com.demo.demoJwt.auth;


import com.demo.demoJwt.config.JwtService;
import com.demo.demoJwt.user.Restaurant;
import com.demo.demoJwt.user.RestaurantRepository;
import com.demo.demoJwt.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final RestaurantRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var restaurant = Restaurant.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.MANAGER)
        .build();

    Optional<Restaurant> restaurant1 =repository.findByEmail(request.getEmail());
    if(!restaurant1.isPresent()){
      repository.save(restaurant);
      var jwtToken = jwtService.generateToken(restaurant);
      return AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
    }
    return null;

  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
