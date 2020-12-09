package com.devsuperior.hroauth.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserFeignClient userFeignClient;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
	
	public User findByEmail(String email) {
		User user = userFeignClient.findByEmail(email).getBody();
		if(user == null) {
			logger.error("Email not found " + email);
			throw new IllegalArgumentException("Email not found");
		}
		logger.info("Email found! " + email);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userFeignClient.findByEmail(username).getBody();
		if(user == null) {
			logger.error("Email not found " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("Email found! " + username);
		return user;
	}
}
