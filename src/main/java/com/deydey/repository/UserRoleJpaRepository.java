package com.deydey.repository;

import com.deydey.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleJpaRepository extends CrudRepository<UserRole, Long> {
	List<UserRole> findByUserId(Long userId);
}
