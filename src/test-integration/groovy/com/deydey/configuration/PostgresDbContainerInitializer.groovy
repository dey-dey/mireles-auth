package com.deydey.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer

class PostgresDbContainerInitializer implements  ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        startPostgresContainer(applicationContext)
    }

    private void startPostgresContainer(ConfigurableApplicationContext appContext) {
        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
                .withDatabaseName("ouath2jwtauthentication")
                .withUsername("dev")
                .withPassword("q1w2e3r4")
        postgreSQLContainer.withExposedPorts(54321)
        postgreSQLContainer.start()
        Integer postgresPort = postgreSQLContainer.getMappedPort(54321);
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(appContext, "spring.datasource.url="+postgreSQLContainer.getJdbcUrl());
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(appContext, "spring.datasource.username=dev");
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(appContext, "spring.datasource.password=q1w2e3r4");
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(appContext, "spring.datasource.driver-class-name=org.postgres.Driver");
    }
}
