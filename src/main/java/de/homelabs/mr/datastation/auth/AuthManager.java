package de.homelabs.mr.datastation.auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HexFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.homelabs.moonrat.javacrypto.helper.CryptKeyHolder;
import de.homelabs.moonrat.javacrypto.helper.RSAHelper;

@Service
public class AuthManager {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private CryptKeyHolder cryptoHelper;
	
	public AuthManager() {
		this.cryptoHelper = RSAHelper.createRSAKeys(2024);
		dump();
	}
	
	protected PublicKey getPublicKey() {
		return this.cryptoHelper.getPublicKey();
	}
	
	protected PrivateKey getPrivateKey() {
		return this.cryptoHelper.getPrivateKey();
	}
	
	private void dump() {
		String publicKey = new String(Base64.getEncoder().encode(this.cryptoHelper.getPublicKey().getEncoded()));
		String privateKey = new String(Base64.getEncoder().encode(this.cryptoHelper.getPrivateKey().getEncoded()));
		String publicKeyHex = HexFormat.of().formatHex(this.cryptoHelper.getPublicKey().getEncoded());
		String privateKeyHex = HexFormat.of().formatHex(this.cryptoHelper.getPrivateKey().getEncoded());
		
		log.debug("authManager, public key : {}", publicKey);
		log.debug("authManager, private key: {}", privateKey);
		
		log.debug("authManager, public key hex: {}", publicKeyHex);
		log.debug("authManager, private key hex: {}", privateKeyHex);
		
		//example text
		String message = "This is an example text!";
		try {
			String cryptedMsg = RSAHelper.encrypt(message, getPublicKey(), true);
			log.debug("cryptedMSG: {}",cryptedMsg);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
