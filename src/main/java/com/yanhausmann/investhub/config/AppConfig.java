package com.yanhausmann.investhub.config;

import com.yanhausmann.investhub.client.BrapiClient;
import com.yanhausmann.investhub.client.dto.BrapiResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BrapiClient brapiClient() {
        return new BrapiClient() {
            @Override
            public BrapiResponseDTO getQuote(String token, String stockId) {
                return null;
            }
        };
    }
}
