package com.risosuit.DGomezTagleProgramacionNCapasMaven.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName.Form;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.Service.UserDetailJPA;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailJPA userDetailJPA;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/Usuario/**")
                .hasAnyRole("Administrador", "Usuario")
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // página de login
                        .defaultSuccessUrl("/Usuario")
                        .failureUrl("/login?error=true") // redirige después de login
                        .permitAll() //Permite que cualquiera acceda al login
                )
                .userDetailsService(userDetailJPA);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
