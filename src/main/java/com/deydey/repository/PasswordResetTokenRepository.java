package com.deydey.repository;

import com.deydey.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<Long, PasswordResetToken> {

	@Query("select t from PasswordResetToken t where expired is null")
	List<PasswordResetToken> findAllUnexpired();

	@Modifying
	@Query("update PasswordResetToken as t set expired = CURRENT_TIMESTAMP where t.expiryDate <= :expiredBefore")
	void setAllExpiredBefore(@Param("expiredBefore") Timestamp expiredBefore);
}
