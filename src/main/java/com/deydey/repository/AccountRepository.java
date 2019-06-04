package com.deydey.repository;

import com.deydey.domain.Account;
import org.jdbi.v3.core.Handle;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
	List<Account> findAll();
	Optional<Account> findById(Long id);
	Account save(Account account);
	Account save(Handle handle, Account account);
}
