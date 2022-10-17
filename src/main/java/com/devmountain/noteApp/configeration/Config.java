package com.devmountain.noteApp.configeration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration// tell spring this class has a method to create beans with @Bean,
// spring will mark this class as a sort of bean factory to be used at runtime.
public class Config {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
