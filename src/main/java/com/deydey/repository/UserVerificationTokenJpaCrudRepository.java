package com.deydey.repository;

import com.deydey.domain.User;
import com.deydey.domain.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface UserVerificationTokenJpaCrudRepository extends CrudRepository<User, VerificationToken> {

}
