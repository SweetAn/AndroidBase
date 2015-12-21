package com.commons.support.util;

import android.util.Log;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加解密帮助类
 * Created by qianjin on 2015/9/22.
 */
public class EncryptionUtil {

    public static String MD5(String source){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(source.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            while (md5.length() < 32)
                md5 = "0" + md5;
            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return "";
        }
    }

    /**
     * SHA加密
     */
    public static byte[] encryptSHA(byte[] data) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sha.update(data);
        return sha.digest();
    }

    /**
     * DES算法，解密
     * @param data  待解密字符串
     * @param decodeKey 解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception  异常
     */
    public byte[] decodeDes(byte[] data,String decodeKey) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(decodeKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            //DES/ECB/PKCS5Padding
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 对字符串加密
     */
    public byte[] encodeDes(byte[] data,String encodeKey) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding");
            DESKeySpec dks = new DESKeySpec(encodeKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
