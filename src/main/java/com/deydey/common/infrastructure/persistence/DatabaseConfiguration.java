package com.deydey.common.infrastructure.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.yml")
public class DatabaseConfiguration {

	private Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	private final Environment environment;

	public DatabaseConfiguration(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("spring.datasource.password"));
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	@Profile("!test")
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
}
