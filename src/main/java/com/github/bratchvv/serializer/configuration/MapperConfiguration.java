package com.github.bratchvv.serializer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladimir Bratchikov
 */
@Configuration
public class MapperConfiguration {

    @Bean
    @Qualifier("defaultObjectMapper")
    ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

}
