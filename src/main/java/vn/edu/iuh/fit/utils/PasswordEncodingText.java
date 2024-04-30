package vn.edu.iuh.fit.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.Base64;
public class PasswordEncodingText implements EncodingText {
    private SecretKey secretKey = null;
    public SecretKey getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    @Override
    public String encrypt(String plainText) throws Exception {
        if (secretKey == null)
            throw new Exception("Secret key is needed. Load secret key");
        byte[] plainTextByte = plainText.getBytes();
        Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encryptedByte);
    }
    public String decrypt(String encryptedText) throws Exception {
        if (secretKey == null)
            throw new Exception("Secret key is needed. Load secret key");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return new String(decryptedByte);
    }
    public void saveKey(String path, Key myKey) throws Exception {
        try (ObjectOutputStream oout = new ObjectOutputStream(new
                FileOutputStream(path))) {
            oout.writeObject(myKey);
        }
    }
    public Key loadKey(String path) throws Exception {
        Key key = null;
        try (ObjectInputStream oout = new ObjectInputStream(new
                FileInputStream(path))) {
            key = (Key) oout.readObject();
        }
        return key;
    }
    public SecretKey createSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(CRYPT_ALGORITHM);
        keyGenerator.init(128); // block size is 128bits
        return keyGenerator.generateKey();
    }
}
