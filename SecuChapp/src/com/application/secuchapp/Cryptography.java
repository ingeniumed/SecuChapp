package com.application.secuchapp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography{
	
     public static byte[] encrypt(byte[] key, byte[] messageDecrypted) throws Exception {
	Cipher c = Cipher.getInstance("AES");
	SecretKeySpec k = new SecretKeySpec(key,"AES");
	c.init(Cipher.ENCRYPT_MODE,k);
	byte[] messageEncrypted = c.doFinal(messageDecrypted);
	
	return messageEncrypted;
      }

      public static byte[] decrypt(byte[] key, byte[] messageEncrypted) throws Exception {
	Cipher c = Cipher.getInstance("AES");
	SecretKeySpec k = new SecretKeySpec(key,"AES");
	c.init(Cipher.ENCRYPT_MODE,k);
	byte[] messageDecrypted = c.doFinal(messageEncrypted);
	
	return messageDecrypted;
      }
}
