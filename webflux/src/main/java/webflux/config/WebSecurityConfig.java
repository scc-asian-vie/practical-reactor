package webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("admin")
      .password(passwordEncoder().encode("password"))
      .roles("ADMIN")
      .build();
    return new MapReactiveUserDetailsService(user);
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
      .csrf().disable()
      .authorizeExchange()
      .pathMatchers("/employee/**").hasRole("ADMIN")
      .pathMatchers("/employees/**").hasRole("ADMIN")
      .pathMatchers("/actuator/**").permitAll()
      .anyExchange().permitAll()
      .and()
      .httpBasic();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
