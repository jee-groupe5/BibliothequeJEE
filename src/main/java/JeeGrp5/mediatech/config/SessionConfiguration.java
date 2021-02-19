package JeeGrp5.mediatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

import java.time.Duration;

@EnableMongoHttpSession
public class SessionConfiguration {

    @Bean
    public JdkMongoSessionConverter mongoSessionConverter() {
        return new JdkMongoSessionConverter(Duration.ofMinutes(15));
    }
}