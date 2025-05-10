package com.noelpinto47.planora;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.noelpinto47.planora.auth.AuthFilter;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import lombok.Data;

@Component
@Configuration
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

    @Bean
    public FilterRegistrationBean<AuthFilter> apiKeyFilter(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/*"); // apply to desired paths
        return registrationBean;
    }
}
