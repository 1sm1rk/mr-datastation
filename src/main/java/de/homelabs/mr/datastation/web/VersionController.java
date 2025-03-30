package de.homelabs.mr.datastation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/api/v1/version")
	public String getVersion() {
		log.debug(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "0.1";
	}
}
