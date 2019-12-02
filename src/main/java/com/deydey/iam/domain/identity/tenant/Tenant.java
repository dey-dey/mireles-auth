package com.deydey.iam.domain.identity.tenant;


import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.application.command.registration.CreateRegistrationCommand;
import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.identity.user.MemberId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class Tenant {

	@Getter
	@Setter
	private TenantId tenantId;
	@Getter
	private Boolean enabled;
	@Getter
	private String name;
	@Getter
	private TenantType tenantType;
	@Getter
	private List<TenantMember> tenantMemberships;
	@Getter
	private AuditInformation auditInformation;

	public static Tenant newPersonalTenant(CreateRegistrationCommand createRegistrationCommand) {
		return Tenant.builder()
				.tenantId(TenantId.getNextValue())
				.name(Tenant.tenantName(createRegistrationCommand.getFirstName(), createRegistrationCommand.getLastName()))
				.tenantType(TenantType.Personal)
				.tenantMemberships(new ArrayList<>())
				.auditInformation(AuditInformation.now())
				.tenantId(TenantId.getNextValue())
				.enabled(false)
				.build();
	}

	public void activate() {
		enabled = Boolean.TRUE;
	}

	public void deactivate() {
		enabled = Boolean.TRUE;
	}

	public void retireTenantMember(MemberId aMemberId) throws IllegalArgumentException {

		TenantMember tenantMember = tenantMemberships.stream()
				.filter(aTenantMember -> aTenantMember.getMemberId() == aMemberId)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("tenant tenantMember was not found"));

		tenantMember.retire();

		tenantMemberships = tenantMemberships.stream()
				.filter(aTenantMember -> aTenantMember.getMemberId() != aMemberId)
				.collect(Collectors.toList());
	}

	public void registerMemberWithRole(MemberId memberId, Set<Role> roles) {
		// TODO check to see if there is more than one personal tenant
		tenantMemberships.add(TenantMember.of(tenantId, memberId, rolesToRoleIds(roles)));
	}

	private Set<RoleId> rolesToRoleIds(Set<Role> roles) {
		return roles.stream().map(Role::getId).collect(Collectors.toSet());
	}

	private static String tenantName(String firstName, String lastName) {
		if (firstName == null && lastName == null) {
			return "";
		}
		if (firstName == null) return lastName;
		return firstName + lastName;
	}

 }
