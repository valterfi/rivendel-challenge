package com.valterfi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableJpaRepositories("com.valterfi.repository")
public class DatabaseConfig extends WebMvcConfigurerAdapter {
    
}
