package de.knallisworld.spring.worker;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("META-INF/spring/spring-config.xml")
@ComponentScan(basePackages = {"de.knallisworld.spring.worker"})
public class AppConfig {
}
