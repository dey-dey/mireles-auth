package com.deydey.common.infrastructure.documentation;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class FlywayConfig {

	@Bean
	@Profile("production")
	public FlywayMigrationStrategy repairMigrationStrategy() {
		return flyway -> {
			flyway.repair();
			flyway.migrate();
		};
	}

	@Bean
	@Profile("development")
	public FlywayMigrationStrategy repairMigrationStrategyDev() {
		return flyway -> {
			flyway.clean();
			flyway.migrate();
		};
	}
}
