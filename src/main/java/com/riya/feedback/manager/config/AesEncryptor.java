package com.riya.feedback.manager.config;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesEncryptor {

	private final SecretKeySpec keySpec;

	private static final String ALGORITHM = "AES";

	// Inject the key via the constructor using @Value
	public AesEncryptor(@Value("${app.encryption.key}") String secretKey) throws Exception {
		// Validate key length before creation
		if (secretKey.length() != 16 && secretKey.length() != 24 && secretKey.length() != 32) {
			throw new IllegalArgumentException("AES Key must be 16, 24, or 32 characters.");
		}
		this.keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
	}

	public String encrypt(String data) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error during AES encryption", e);
		}
	}

	public String decrypt(String encryptedData) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
		} catch (Exception e) {
			throw new RuntimeException("Error during AES decryption", e);
		}
	}
}