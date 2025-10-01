package com.quyet.identity.configuration;

import com.quyet.identity.common.UriPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

  private final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource;

  public SecurityConfig(UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource) {
    this.urlBasedCorsConfigurationSource = urlBasedCorsConfigurationSource;
  }

  private final String[] publicEndpoints = {
    UriPath.V1 + UriPath.AUTH + "/**",
  };

  private final String[] publicPostEndpoints = {
    UriPath.V1 + UriPath.USERS + "/**",
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(urlBasedCorsConfigurationSource))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(publicEndpoints)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, publicPostEndpoints)
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    http.csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
