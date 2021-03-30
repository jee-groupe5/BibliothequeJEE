package JeeGrp5.mediatech.config;

import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@EnableMongoHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SessionConfiguration {
}