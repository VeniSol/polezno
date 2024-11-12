package org.example.polezno.sequrity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.polezno.Entities.Perms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    private final SessionFilter sessionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requgest -> request

                        .requestMatchers("/admin/**").hasAuthority(Perms.ADMIN.getPermission())
                        .requestMatchers("/order").hasAuthority(Perms.USER.getPermission())
                        .requestMatchers("/account").hasAuthority(Perms.USER.getPermission())
                        .requestMatchers("/saveUserInfo").hasAuthority(Perms.USER.getPermission())
                        .requestMatchers("/addAddress").hasAuthority(Perms.USER.getPermission())
                        .requestMatchers("/delAddress").hasAuthority(Perms.USER.getPermission())
//                        .requestMatchers("/orderDelete/**").hasAuthority(Perms.USER.getPermission())

                        .anyRequest().permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS)).
                logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("Haruka"))
                .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}