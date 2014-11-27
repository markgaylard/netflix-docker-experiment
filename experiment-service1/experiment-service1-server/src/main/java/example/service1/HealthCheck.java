package example.service1;

import javax.annotation.PostConstruct;

import com.google.inject.Singleton;
import com.netflix.karyon.health.HealthCheckHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HealthCheck implements HealthCheckHandler {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheck.class);

    @PostConstruct
    public void init() {
        logger.info("Health check initialized.");
    }

    @Override
    public int getStatus() {
        // TODO: Health check logic.
        logger.info("Health check invoked.");
        return 200;
    }
}