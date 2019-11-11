package com.deydey.common.infrastructure.spring;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApplicationConfig {
	private String secret;
}
