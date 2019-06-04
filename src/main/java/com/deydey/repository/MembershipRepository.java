package com.deydey.repository;

import com.deydey.domain.Membership;
import org.jdbi.v3.core.Handle;

import java.util.Optional;

public interface MembershipRepository {
	Membership save(Membership membership);
	Membership save(Handle handle, Membership membership);
}
