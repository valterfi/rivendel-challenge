package com.valterfi.config;

import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
  @Bean
  public Docket swaggerSpringMvcPlugin() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("Rivendel Challenge")
        .select()
        .paths(regex("/api/.*"))
        .build();
  }
}
