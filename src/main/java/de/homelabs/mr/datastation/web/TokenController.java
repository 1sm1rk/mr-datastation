package de.homelabs.mr.datastation.web;

import java.security.InvalidAlgorithmParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.homelabs.mr.datastation.auth.DatastationService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TokenController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private DatastationService datastationService;
	
	public TokenController(DatastationService pdatastationService) {
		this.datastationService = pdatastationService;
	}
	
	@GetMapping("/api/v1/token/publickey")
	public ResponseEntity<byte[]> getPublicKey(HttpServletRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/pkcs8");
		
		//encode base64
		//byte[] data = datastationService.getPublicKey().getEncoded();
		//String output = new String(Base64.getEncoder().encode(data));
		
		byte[] output = datastationService.getPublicKey(true);
		log.debug("public key requested: {}", request.getRemoteAddr());
		
		return new ResponseEntity<byte[]>(output, headers, HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/token/register")
	public ResponseEntity<String> registerClient(@RequestBody byte[] clientMessage) {
		//we expect a message encrypted with the public key
		log.debug("client request received: {}", new String(clientMessage));
		
		try {
			String token = datastationService.handleRegisterClientRequest(clientMessage);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (InvalidAlgorithmParameterException e) {
			log.error("cannot decrypt client message: {}",e.getMessage());
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
