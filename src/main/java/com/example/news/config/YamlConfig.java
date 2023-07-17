package com.example.news.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.configuration")
public class YamlConfig {
    private List<String> corsAllowedList = new ArrayList<>();
    private List<String> publicApiList = new ArrayList<>();

}
