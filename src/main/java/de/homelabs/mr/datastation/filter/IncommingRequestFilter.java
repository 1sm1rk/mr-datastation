package de.homelabs.mr.datastation.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IncommingRequestFilter extends OncePerRequestFilter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	private BearerTokenResolver tokenResolver = new BearerTokenResolver();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug("RequestURI: "+request.getRequestURI());
	
		String token = tokenResolver.resolve(request);
		
		log.debug("token: "+token);
		
		if (StringUtils.hasLength(token)) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("test"));
			Authentication auth = new UsernamePasswordAuthenticationToken("a", "b", authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		//do the rest of the filter
		filterChain.doFilter(request, response);
	}
}

