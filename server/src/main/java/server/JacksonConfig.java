package server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addHibernateProxy() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .mixIn(HibernateProxy.class, HibernateProxyMixin.class);
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private static abstract class HibernateProxyMixin {
    }
}
