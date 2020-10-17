package com.r7.core.common.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * jackson序列化配置
 *
 * @author zhongpingli
 */
@Configuration
public class JacksonConfig {

    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
    }

}
