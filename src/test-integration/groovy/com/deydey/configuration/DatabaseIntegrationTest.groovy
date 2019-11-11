package com.deydey.configuration

import com.deydey.Oauth2JwtAuthenticationApplication
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared

import java.beans.Statement
import java.sql.ResultSet

@SpringBootTest(
        classes = [Oauth2JwtAuthenticationApplication, TestDatabaseConfiguration]
)
@Slf4j
@ContextConfiguration(initializers = [PostgresDbContainerInitializer])
abstract class DatabaseIntegrationTest extends BaseIntegrationTest {
}