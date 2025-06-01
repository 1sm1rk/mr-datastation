package de.homelabs.mr.datastation.web;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import de.homelabs.mr.datastation.auth.DatastationService;

@Controller
public class KeyController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private DatastationService datastationService;
	
	public KeyController(DatastationService pdatastationService) {
		this.datastationService = pdatastationService;
	}
	
	@GetMapping("/api/ds/version")
	public ResponseEntity<String> getVersion() {
		return new ResponseEntity<String>("mr datastation v1", HttpStatus.OK);
	}
	
	@GetMapping("/api/ds/v1/publickey")
	public ResponseEntity<String> getPublicKey() {		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/pkcs8");
		byte[] data = datastationService.getPublicKey(true);
		
		String output = new String(Base64.getEncoder().encode(data)); 
		return new ResponseEntity<String>(output, headers, HttpStatus.OK);
	}
}
