package com.risosuit.DGomezTagleProgramacionNCapasMaven.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.Handler.CustomAuthenticationFailureHandler;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.Handler.CustomLogoutSuccessHandler;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.Service.UserDetailJPA;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailJPA userDetailJPA;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfiguration(UserDetailJPA userDetailJPA) {
        this.userDetailJPA = userDetailJPA;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/Usuario/**")
                .hasAnyRole("Administrador", "Usuario")
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") 
                        .defaultSuccessUrl("/Usuario")
                        .failureHandler(customAuthenticationFailureHandler) // Usar el handler
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout") 
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID") 
                        .permitAll())
                .userDetailsService(userDetailJPA);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}