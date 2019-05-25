package com.deydey.config;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApplicationConfig {
	private String secret;
}
