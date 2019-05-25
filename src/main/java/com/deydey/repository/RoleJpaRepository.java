package com.deydey.repository;


import com.deydey.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleJpaRepository extends CrudRepository<Role, Long> {
}
