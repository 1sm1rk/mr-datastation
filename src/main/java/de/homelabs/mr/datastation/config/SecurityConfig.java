package de.homelabs.mr.datastation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import de.homelabs.mr.datastation.filter.IncommingRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private IncommingRequestFilter incommingRequestFilter;
	
	public SecurityConfig(IncommingRequestFilter incommingRequestFilter) {
		this.incommingRequestFilter = incommingRequestFilter;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
					//.requestMatchers("").permitAll()
					.requestMatchers("/api/key/v1/publickey").permitAll()
					//.requestMatchers("/test").permitAll()
					.anyRequest().authenticated()
			)
			//.authenticationProvider(authenticationProvider())
			.addFilterBefore(incommingRequestFilter, BasicAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		// ALTHOUGH THIS SEEMS LIKE USELESS CODE,
	    // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
		return config.getAuthenticationManager();
	}


}