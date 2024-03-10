package ru.hse.software.authentication;

import ru.hse.software.exceptions.EncryptionException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.KeyException;

public class AuthenticationEncryptor {
    Cipher encryptCipher;

    public AuthenticationEncryptor() {
        try {
            encryptCipher = Cipher.getInstance("AES");
        } catch (GeneralSecurityException ex) {
            throw new EncryptionException("Не удалось получить алгоритм шифрования :(", ex);
        }
        SecretKeySpec key = new SecretKeySpec("aRZdCEwHqqgJ9lvt".getBytes(), "AES");
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (KeyException ex) {
            throw new EncryptionException("Не удалось применить ключ шифрования :(", ex);
        }
    }

    public String encryptPass(String pass) {
        byte[] encryptedPassBytes;
        try {
            encryptedPassBytes = encryptCipher.doFinal(pass.getBytes());
        } catch (GeneralSecurityException ex) {
            throw new EncryptionException("Ошибка во время шифрования :(", ex);
        }
        return new String(encryptedPassBytes);
    }
}
