package com.deydey.iam.application.service;

import com.deydey.iam.domain.identity.user.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserDetailsService.class);

	public ApplicationUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Member member = Member.builder().build();
			if (member == null) {
				LOGGER.debug("user not found with the provided email");
				return null;
			}
			LOGGER.debug(" user from username " + member.toString());
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("COMPANY_MEMBER"));

//			 List<GrantedAuthority> authorities = new ArrayList<>();
//		    for (Role role: roles) {
//		        authorities.add(new SimpleGrantedAuthority(role.getName()));
//		        role.getPrivileges().stream()
//		         .map(p -> new SimpleGrantedAuthority(p.getName()))
//		         .forEach(authorities::add);
//		    }
//     
			return new org.springframework.security.core.userdetails.User(member.getEmail(),
					member.getAuthentication().getPassword(),
					authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}
}
