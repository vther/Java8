package com.vther.java.security.des;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DesMain {
    private static final String SOURCE_STR = "vther's test";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, DecoderException {
        // 生成Key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        // Key转换
        DESKeySpec desKeySpec = new DESKeySpec(encoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey desSecretKey = factory.generateSecret(desKeySpec);

        // 加密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, desSecretKey);
        byte[] bytes = cipher.doFinal(SOURCE_STR.getBytes());

        String encryptResult = Hex.encodeHexString(bytes);
        System.out.println("encryptResult = " + encryptResult);


        cipher.init(Cipher.DECRYPT_MODE, desSecretKey);
        byte[] decryptBytes = cipher.doFinal(Hex.decodeHex(encryptResult.toCharArray()));
        System.out.println("decryptResult = " + new String(decryptBytes));
    }


}
