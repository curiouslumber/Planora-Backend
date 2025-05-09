package com.noelpinto47.planora;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

// import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PlanoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanoraApplication.class, args);
	}

	@Bean
	DataSource dataSource() {
		String url = System.getenv("MYSQL_URL");
		String username = System.getenv("MYSQL_USERNAME");
		String password = System.getenv("MYSQL_PASSWORD");

		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.url(url)
				.username(username)
				.password(password)
				.build();
	}
}
