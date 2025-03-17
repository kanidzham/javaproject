package ru.kani.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kani.Application;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class SpringConfig {
}
