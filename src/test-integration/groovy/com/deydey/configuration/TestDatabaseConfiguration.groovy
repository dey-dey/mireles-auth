package com.deydey.configuration

import com.deydey.common.infrastructure.spring.ApplicationConfig
import com.deydey.common.infrastructure.spring.security.WebSecurityConfig
import com.deydey.iam.application.service.ApplicationUserDetailsService
import com.deydey.iam.application.service.SecurityService
import com.deydey.iam.domain.access.authentication.CredentialsService
import com.deydey.iam.domain.identity.user.MemberRepository
import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared

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
