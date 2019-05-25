package com.deydey.repository;

import com.deydey.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserJpaCrudRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
}
