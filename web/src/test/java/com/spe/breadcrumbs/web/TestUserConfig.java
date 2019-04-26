package com.spe.breadcrumbs.web;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import java.util.Arrays;

@TestConfiguration
public class TestUserConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        User basic = new User("user@test.com","password"
                ,Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));


        User admin = new User("jackSmith@hotmail.co.uk","aurora44",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")
                ,new SimpleGrantedAuthority("ROLE_ADMIN")));
        return new InMemoryUserDetailsManager(Arrays.asList(basic,admin));
    }
}
