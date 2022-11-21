package com.backend.controller;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

public class CipherController {
	private static Logger Log = LoggerFactory.getLogger(CipherController.class);

	public String encriptKeyDES(String data) {

		try {
			DESKeySpec keySpec = new DESKeySpec("ionix123456".getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(keySpec);
			byte[] cleartext = data.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			String encrypted = Base64Utils.encodeToString(cipher.doFinal(cleartext));
			return encrypted;
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Error : " + e.getMessage());
			return e.getMessage();
		}
	}
	
	public boolean validarRut(String rut) {

	    boolean validacion = false;
	    try {
	        rut =  rut.toUpperCase();
	        rut = rut.replace(".", "");
	        rut = rut.replace("-", "");
	        int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

	        char dv = rut.charAt(rut.length() - 1);

	        int m = 0, s = 1;
	        for (; rutAux != 0; rutAux /= 10) {
	            s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
	        }
	        if (dv == (char) (s != 0 ? s + 47 : 75)) {
	            validacion = true;
	        }

	    } catch (java.lang.NumberFormatException e) {
	    } catch (Exception e) {
	    }
	    return validacion;
	}
}
