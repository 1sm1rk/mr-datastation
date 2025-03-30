package de.homelabs.mr.datastation.auth;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.homelabs.moonrat.javacrypto.helper.RSAHelper;

@Service
public class DatastationService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private AuthManager authManager;
	
	public DatastationService(AuthManager pAuthManager) {
		this.authManager = pAuthManager;
	}
	
	
	public byte[] getPublicKey(boolean encodeBase64) {
		if (encodeBase64) {
			return Base64.getEncoder().encode(authManager.getPublicKey().getEncoded());
		} else {
			return this.authManager.getPublicKey().getEncoded();
		}
		
	}
	
	public byte[] getEncryptedMessage(byte[] message, boolean encodedBase64) {
		try {
			return RSAHelper.encrypt(message, authManager.getPublicKey(), encodedBase64);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public byte[] getDecryptedClientMessage(byte[] message, boolean encodedBase64) throws InvalidAlgorithmParameterException {
		try {
			return RSAHelper.decrypt(message, authManager.getPrivateKey(), encodedBase64);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			log.error(e.getMessage());
			throw new InvalidAlgorithmParameterException(e.getMessage());
		}
	}
	
	public String handleRegisterClientRequest(byte[] clientRequest) throws InvalidAlgorithmParameterException {
		byte[] message = getDecryptedClientMessage(clientRequest, true);
		
		//handleRequest
			
		//return token
		return new String(message);
	}
}
