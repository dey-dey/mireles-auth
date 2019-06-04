package com.deydey.repository;

import com.deydey.domain.Account;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.config.JdbiCache;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AccountJdbiRepository implements AccountRepository {

	private final Jdbi jdbi;
	private static final String INSERT_ACCOUNT = "INSERT INTO " +
			"account(plan_level, updated_at) " +
			"VALUES (:planLevel, now());";
	private final String SELECT_ALL = "select * from account";
	private final String SELECT_BY_ID = "select * from account ac where ac.id = :id";

	@Autowired
	public AccountJdbiRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	@Override
	public List<Account> findAll() {
		return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL).mapTo(Account.class).list());
	}

	@Override
	public Optional<Account> findById(Long id) {
		return jdbi.withHandle(handle ->
				handle.createQuery(SELECT_BY_ID)
						.bind("id", id)
						.mapTo(Account.class)).findFirst();
	}

	@Override
	public Account save(Account account) {
		return jdbi.withHandle(handle -> save(handle, account));
	}

	@Override
	public Account save(Handle handle, Account account) {
		return handle.createUpdate(INSERT_ACCOUNT)
				.bind("planLevel", account.getPlanLevel())
				.executeAndReturnGeneratedKeys()
				.map(new AccountMapper())
				.one();
	}

	public class AccountMapper implements RowMapper<Account> {

		@Override
		public Account map(ResultSet rs, StatementContext ctx) throws SQLException {
			return Account.builder().id(rs.getLong("id")).build();
		}
	}
}
