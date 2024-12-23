package com.doc.swagger.SwaggerDocumentation.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("Spring Boot Open API")
                                .version("1.0")
                        .description("API documentation for spring boot application")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")));
    }
}
