package com.deydey.configuration

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.testcontainers.spock.Testcontainers
import javax.sql.DataSource


@TestConfiguration
@Slf4j
@Testcontainers
class TestDatabaseConfiguration {
    @Bean
    @Qualifier("jdbcTemplate")
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load()
        flyway.migrate()
        return flyway
    }
}
