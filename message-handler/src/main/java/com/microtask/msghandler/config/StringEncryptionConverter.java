package com.microtask.msghandler.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.core.codec.DecodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Converter
public class StringEncryptionConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final Key AES_KEY = new SecretKeySpec("00016-bit-secret".getBytes(), "AES");
    @Override
    public String convertToDatabaseColumn(String ccNumber) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, AES_KEY);
            return  Base64.getEncoder().encodeToString(c.doFinal(ccNumber.getBytes()));
        } catch (Exception e) {
            throw new DecodingException("String encoding failed", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, AES_KEY);
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new DecodingException("String decoding failed", e);
        }
    }
}