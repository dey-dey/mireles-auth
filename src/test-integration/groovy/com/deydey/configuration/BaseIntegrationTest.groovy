package com.deydey.configuration

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.sql.DataSource

@ActiveProfiles(profiles = 'test', resolver = EnvironmentAwareActiveProfilesResolver)
abstract class BaseIntegrationTest extends Specification {

    @Autowired
    private Flyway flyway

    @Autowired
    DataSource dataSource

    def setup() {
        flyway.migrate()
    }

    def cleanup() {
        flyway.clean()
    }

}
