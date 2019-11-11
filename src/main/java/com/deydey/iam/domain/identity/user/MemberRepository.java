package com.deydey.iam.domain.identity.user;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> save(Member member);
}
