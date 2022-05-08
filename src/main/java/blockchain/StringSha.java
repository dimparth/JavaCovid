package blockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringSha {
    public static String sha256(String input) {
        try {
            MessageDigest sha
                    = MessageDigest
                    .getInstance(
                            "SHA-256");
            int i = 0;
            byte[] hash
                    = sha.digest(
                    input.getBytes(StandardCharsets.UTF_8));
            // to hexHash perilamvanei
            // to Hexadecimal hash
            StringBuffer hexHash
                    = new StringBuffer();
            while (i < hash.length) {
                String hex
                        = Integer.toHexString(
                        0xff & hash[i]);
                if (hex.length() == 1)
                    hexHash.append('0');
                hexHash.append(hex);
                i++;
            }
            return hexHash.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
