package com.noelpinto47.planora;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.boot.jdbc.DataSourceBuilder;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "spring")
@Data
public class PlanoraConfiguration {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Data
    public static class DataSourceProperties {
        private String url;
        private String username;
        private String password;

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url(url)
                    .username(username)
                    .password(password)
                    .build();
        }
    }
}
