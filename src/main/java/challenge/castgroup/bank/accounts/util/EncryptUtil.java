package challenge.castgroup.bank.accounts.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/***
 * 
 * @author robson.a.mello
 *
 */
public class EncryptUtil {

	private EncryptUtil() {
	}

	public static String encryptPassword(String password) {
		String sha256 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-256");
			crypt.reset();
			crypt.update(password.getBytes(StandardCharsets.UTF_8));
			sha256 = bytesToHex(crypt.digest());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return sha256;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
