package ar.edu.ubp.das.streamingstudio.sstudio.config;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxConfig {
    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    @Bean
    public Bus cxfBus() {
        Bus bus = new org.apache.cxf.bus.spring.SpringBus();
        bus.getFeatures().add(loggingFeature());
        return bus;
    }
}
