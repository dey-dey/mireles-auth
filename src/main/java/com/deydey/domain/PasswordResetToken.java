package com.deydey.domain;

import com.google.common.annotations.VisibleForTesting;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="password_reset_token")
@Builder
public class PasswordResetToken extends Auditable {

	private static final long EXPIRATION = 60L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="token")
	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(name="expiry_date")
	private Instant expiryDate;

	@Column
	@Nullable
	private Timestamp expired;

	public void setExpiryDate() {
		this.expiryDate = Instant.now();
	}

	@VisibleForTesting
	public void setExpiryDateTest(Instant time) {
		this.expiryDate = time;
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = Instant.now();
	}
}
