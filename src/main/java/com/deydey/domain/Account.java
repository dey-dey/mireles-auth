package com.deydey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class Account {

	private Long id;

	private AccountPlanLevel planLevel;

	private Instant createdAt;

	private Instant updatedAt;
}