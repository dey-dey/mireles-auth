package com.deydey.common.infrastructure.persistence;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FlywayConfig {

	@Bean
	public FlywayMigrationStrategy repairMigrationStrategy() {
		return flyway -> {
			flyway.repair();
			flyway.migrate();
		};
	}
}
