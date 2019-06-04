package com.deydey.service;

import com.deydey.domain.Membership;
import com.deydey.domain.Role;
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
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Membership membership = Membership.builder().build();
			if (membership == null) {
				LOGGER.debug("user not found with the provided email");
				return null;
			}
			LOGGER.debug(" user from username " + membership.toString());
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("COMPANY_MEMBER"));
			return new org.springframework.security.core.userdetails.User(membership.getEmail(), membership.getLoginPassword(), authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}
}
