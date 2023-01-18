package com.example.demospringsecuritylearning.security;

import com.example.demospringsecuritylearning.jwt.JwtUsernameAndPasswordFilter;
import com.example.demospringsecuritylearning.model.UserRole;
import com.example.demospringsecuritylearning.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    private final UserService userService;

    public ApplicationSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                //.requestMatchers("/api/v1/tests/**").permitAll() //marca o que nao precisa estar autenticado

                .requestMatchers("/api/v1/students/**").hasRole(UserRole.USER.name()) //permite entrar quem é user
                .requestMatchers("/api/v1/tests/**").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .csrf().disable();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;

    }


    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(daoAuthenticationProvider());
    }

//    @Bean
//    protected UserDetailsService userDetailsService(){
//        UserDetails adm = User.builder()
//                .username("adm")
//                .password(passwordEncoder().encode("password"))
//                .roles(UserRole.ADMIN.name())
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles(UserRole.USER.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(adm, user);
//    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}


