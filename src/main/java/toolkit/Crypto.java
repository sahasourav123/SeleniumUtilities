package toolkit;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	private String key;

	public Crypto(String key) {
		this.key = key;
	}

	protected String get(String text) {
		String outVal = "";

		try {
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = text.getBytes();

			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			outVal = new String(cipher.doFinal(encrypted));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return outVal;
	}

	protected String set(String text) {
		String outVal = "";

		try {
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			outVal = new String(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return outVal;
	}

}