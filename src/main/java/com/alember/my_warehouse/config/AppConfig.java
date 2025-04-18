package com.alember.my_warehouse.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    @Bean
    private ObjectMapper objectMapper(){
        var ObjectMapper = new ObjectMapper();
        objectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper();
    }

}
