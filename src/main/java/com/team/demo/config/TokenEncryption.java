package com.team.demo.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class TokenEncryption {

    private static final String ALGORITHM = "AES";
    private static final byte[] SECRET_KEY = "your_secret_key_bytes".getBytes();

    public static String encryptToken(String token) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY, ALGORITHM));
        byte[] encryptedTokenBytes = cipher.doFinal(token.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedTokenBytes);
    }

    public static String decryptToken(String encryptedToken) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY, ALGORITHM));
        byte[] decryptedTokenBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedToken));
        return new String(decryptedTokenBytes);
    }
}

