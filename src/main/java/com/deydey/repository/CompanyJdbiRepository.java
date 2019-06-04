package com.deydey.repository;

import com.deydey.domain.Account;
import com.deydey.domain.Company;
import com.deydey.domain.CompanyAccessLevel;
import lombok.AccessLevel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Transactional
public class CompanyJdbiRepository implements CompanyRepository {

	private final Jdbi jdbi;

	private final String SAVE_COMPANY = "INSERT INTO company" +
			"(account_id, name, access_level, updated_at) " +
			"VALUES (:accountId, :name, :accessLevel, now())";

	public CompanyJdbiRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	@Override
	public Company save(Company company) {
		return jdbi.withHandle(handle -> save(handle, company));
	}

	@Override
	public Company save(Handle handle, Company company) {
		return handle.createUpdate(SAVE_COMPANY)
				.bind("accountId", company.getAccount().getId())
				.bind("name", company.getName())
				.bind("accessLevel", company.getAccessLevel())
				.executeAndReturnGeneratedKeys()
				.map(new CompanyMapper())
				.one();
	}

	public class CompanyMapper implements RowMapper<Company> {

		@Override
		public Company map(ResultSet rs, StatementContext ctx) throws SQLException {
			return Company.builder()
					.id(rs.getLong("id"))
					.accessLevel(CompanyAccessLevel.valueOf(rs.getString("access_level")))
					.account(Account.builder().id(rs.getLong("account_id")).build())
					.createdAt(rs.getTimestamp("created_at").toInstant())
					.updatedAt(rs.getTimestamp("updated_at").toInstant())
					.build();
		}
	}
}
