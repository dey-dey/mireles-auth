package com.deydey.domain;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable {


	@CreatedDate
//	@Temporal(TemporalType.TIMESTAMP)
	protected Instant created_at;


	@LastModifiedDate
//	@Temporal(TemporalType.TIMESTAMP)
	protected Instant updated_at;

}