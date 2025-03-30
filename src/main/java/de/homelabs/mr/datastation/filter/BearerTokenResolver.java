package de.homelabs.mr.datastation.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @see package org.springframework.security.oauth2.resourceserver.web:DefaultBearerTokenResolver

 */
public class BearerTokenResolver {
	private final static Logger log = LoggerFactory.getLogger(BearerTokenResolver.class);
	private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$");
	
	public String resolve(HttpServletRequest request) {
		return resolveFromAuthorizationHeader(request);
	}

	private static String resolveFromAuthorizationHeader(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		
		log.debug("AuthHeader: ", authorization);
		
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
			Matcher matcher = authorizationPattern.matcher(authorization);

			if (!matcher.matches()) {
				log.error("invalid token: "+authorization);
			}

			return matcher.group("token");
		}
		return null;
	}
}
