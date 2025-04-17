package com.alember.my_warehouse.config;

import com.alember.my_warehouse.services.security.WarehouseUserDetails;
import com.alember.my_warehouse.services.security.WarehouseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class MySecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/product/").hasRole("USER")
                        .requestMatchers("/api/suppliers").hasRole("ADMIN")
                        .requestMatchers("/api/category/**").authenticated()
                        .requestMatchers("/api/user/register/").permitAll()
                        .requestMatchers("/api/user/login/").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,WarehouseUserDetailsService userDetailsService) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }


//    Manual set username and password
//    @Bean
//    UserDetailsService userDetailsService(){
//
//        var encoder = passwordEncoder();
//        String password = "admin@123";
//
//        UserDetails user1 = User.withUsername("admin").password(encoder.encode(password)).roles("ADMIN").build();
//        UserDetails user = User.withUsername("user").password(encoder.encode("password")).roles("USER").build();
//
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(List.of(user1, user));
//
//        return userDetailsManager;
//    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
