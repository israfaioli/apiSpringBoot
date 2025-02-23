package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean //serve para devolver um objeto para o spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //crsf disable é para desabilitar a proteção contra ataques Cross-Site Request Forgery, como vmaos trabalhar autenticacao com token, ela ja é uma proteção contra este ataque ;]
        return http.csrf(csrf -> csrf.disable())
                        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(req -> {
                            req.requestMatchers("/login").permitAll();
                            req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                            req.anyRequest().authenticated().and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
                        }).build();
    }

    @Bean //ensinar o spring como cria um objeto que ele irá usar internamente
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean //ensinar o spring como cria um objeto que ele irá usar internamente
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
