package com.deydey.service;

import com.deydey.domain.User;
import com.deydey.domain.UserRole;
import com.deydey.repository.UserJpaCrudRepository;
import com.deydey.repository.UserRoleJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

	private final UserJpaCrudRepository userRepository;

	private final UserRoleJpaRepository userRoleJpaRepository;

	@Autowired
	public CustomUserDetailsService(UserJpaCrudRepository userRepository, UserRoleJpaRepository userRoleJpaRepository) {
		this.userRepository = userRepository;
		this.userRoleJpaRepository = userRoleJpaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				LOGGER.debug("user not found with the provided email");
				return null;
			}
			LOGGER.debug(" user from username " + user.toString());

			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}

	public Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		List<UserRole> userRoles = userRoleJpaRepository.findByUserId(user.getId());
		for (UserRole userRole : userRoles) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getRole().getRole());
			authorities.add(grantedAuthority);
		}
		LOGGER.debug("user authorities are " + authorities.toString());
		return authorities;
	}


}
