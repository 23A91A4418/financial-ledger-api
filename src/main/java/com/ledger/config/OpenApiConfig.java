package com.ledger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Financial Ledger API")
                        .description("A robust double-entry bookkeeping API with accounts, transactions, transfers, and full ledger tracking.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your-email@example.com")
                                .url("https://github.com/your-profile")
                        )
                );
    }
}
