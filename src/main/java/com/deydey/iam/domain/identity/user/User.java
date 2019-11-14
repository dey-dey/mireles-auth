package com.deydey.iam.domain.identity.user;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.application.command.registration.CreateRegistrationCommand;
import com.deydey.iam.domain.identity.tenant.TenantId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Builder
public class User {

	@Getter
	@Setter
	private UserId id;
	@Getter
	private UserIdentityInformation userIdentityInformation;
	@Getter
	private AuditInformation auditInformation;
	@Getter
	@Setter
	private TenantId tenantId;

	private List<Member> members;

	public static User of(TenantId tenantId,
						CreateRegistrationCommand createRegistrationCommand) {
		return User.builder()
				.tenantId(tenantId)
				.userIdentityInformation(
					new UserIdentityInformation(createRegistrationCommand.getFirstName(),
						createRegistrationCommand.getLastName(),
						createRegistrationCommand.getEmail()))
				.id(new UserId(UserId.nextValue()))
				.members(new ArrayList<>())
				.auditInformation(AuditInformation.now())
				.build();
	}

	@Override
	public String toString() {
		return userIdentityInformation.getFirstName() + " " +
				userIdentityInformation.getLastName() + " " +
				id.getValue();
	}

	public void setPrimaryMember(Member aMemberShip) {
		if (members == null) {
			throw new IllegalStateException("members have not meen insitialized");
		}

		if (members.size() == 0) {
			aMemberShip.makePrimaryStatus();
			members.add(aMemberShip);
			return;
		}


		for (Member member : members) {
			member.removePrimaryStatus();
		}
		aMemberShip.makePrimaryStatus();
	}

	public Member getPrimary() {
		if (members == null) {
			throw new IllegalStateException("getPrimary: User must have a membership");
		}
		return members.stream().filter(Member::isPrimary).findFirst().orElse(null);
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public String fullName() {
		return userIdentityInformation.getFullName();
	}

	public String defaultEmail() {
		return userIdentityInformation.getDefaultEmail();
	}
}