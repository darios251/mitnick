package com.mitnick.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("loginUtils")
public class LoginUtils {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher;

	public Authentication authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		Authentication auth = authenticationProvider.authenticate(token);
		if (null != auth) {
			SecurityContextHolder.getContext().setAuthentication(auth);

			defaultAuthenticationEventPublisher.publishAuthenticationSuccess(auth);
			return auth;
		}
		throw new BadCredentialsException("null authentication");
	}

}
