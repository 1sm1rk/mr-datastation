package de.homelabs.mr.datastation.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.homelabs.moonrat.javacrypto.helper.CryptKeyHolder;
import de.homelabs.moonrat.javacrypto.helper.RSAHelper;

@Service
public class DatastationService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private CryptKeyHolder cryptoHelper;
	
	public DatastationService() {
		this.cryptoHelper = RSAHelper.createRSAKeys(2024);
	}
	
	public PublicKey getPublicKey() {
		return this.cryptoHelper.getPublicKey();
	}
	
	public byte[] getEncryptedMessage(byte[] message, PublicKey key, boolean encodedBase64) {
		try {
			return RSAHelper.encrypt(message, key, encodedBase64);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
