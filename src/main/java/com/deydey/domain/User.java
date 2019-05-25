package com.deydey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class User extends Auditable {
	public User(){}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	@Size(min = 1, max = 100)
	private String email;

	@Column
	private String password;

	@Column
	@Nullable
	private Character enabled;

	@Override
	public String toString() {
		return email;
	}

	public void setEnabled() {
		enabled = ' ';
	}
}
