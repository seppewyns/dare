package v7;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.*;

/**
 * Provides methods to hash data using SHA-256.
 */
public class SHA256 {
    /**
     * Hashes a string of data using SHA-256.
     * @param data The data to hash.
     * @return The hashed data, in base64 format.
     */
    public static String hash(String data) {
        // from https://stackoverflow.com/a/43294412
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            // should never happen
            throw new RuntimeException("SHA-256 is not available.", e);
        }
    }
}
