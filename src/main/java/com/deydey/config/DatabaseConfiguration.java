package com.deydey.config;

import com.deydey.domain.Account;
import com.deydey.domain.Membership;
import com.deydey.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DatabaseConfiguration {


	@Bean
	public Jdbi jdbi(DataSource dataSource) {
		// idea from https://mdeinum.github.io/2018-08-13-Use-JDBI-With-Spring-Boot/
		TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy(dataSource);
		Jdbi jdbi = Jdbi.create(dataSourceProxy);
		jdbi.installPlugin(new PostgresPlugin());
		jdbi.installPlugin(new SqlObjectPlugin());
		return jdbi;
	}
}
