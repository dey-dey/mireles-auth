package com.deydey.iam.application.translator;

import com.deydey.iam.api.dto.RegistrationDto;
import com.deydey.iam.api.dto.RegistrationState;
import com.deydey.iam.domain.identity.tenant.Tenant;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.User;

public class RegistrationTranslator {

	public static RegistrationDto of(User user, Member member, Tenant tenant) {
		return new RegistrationDto(tenant.getTenantId().getValue(),
				user.getId().getValue(),
				member.getId().getValue(),
				user.fullName(),
				user.defaultEmail(),
				RegistrationState.MemberVerificationPending);
	}
}
