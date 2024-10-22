package v7;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Base64;

import java.security.interfaces.*;
import java.security.spec.*;
import java.security.*;
import java.nio.file.*;
import javax.crypto.*;

/**
 * Provides methods to sign and verify messages using RSA.
 */
public class RSA {

    /**
     * The path of the private key.
     */
    static final Path PRIVATE_KEY_PATH = Paths.get(System.getProperty("user.home"), "private.key");
    /**
     * The path of the public key.
     */
    static final Path PUBLIC_KEY_PATH = Paths.get(System.getProperty("user.home"), "public.key");

    /**
     * Generates a public/private key pair and persists it in the private and public key files.
     */
    public static void generate() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        Files.write(PRIVATE_KEY_PATH, privateKey.getEncoded());
        Files.write(PUBLIC_KEY_PATH, publicKey.getEncoded());
    }

    /**
     * Gets the RSA key factory.
     * @return An RSA KeyFactory.
     */
    private static KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the private key.
     * @return The RSA private key.
     */
    public static RSAPrivateKey getPrivateKey() {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(PRIVATE_KEY_PATH));
            return (RSAPrivateKey) getKeyFactory().generatePrivate(keySpec);
        } catch (IOException e) {
            throw new RuntimeException("Could not read private key.", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the public key.
     * @return The RSA public key.
     */
    public static RSAPublicKey getPublicKey() {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Files.readAllBytes(PUBLIC_KEY_PATH));
            return (RSAPublicKey) getKeyFactory().generatePublic(keySpec);
        } catch (IOException e) {
            throw new RuntimeException("Could not read public key.", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypts a string of data using the private key.
     * @param data The data.
     * @return The encrypted data, in base64 format.
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey());
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Could not encrypt data.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA is not available.", e);
        }
    }

    /**
     * Verifies whether a signature matches a hash.
     * @param signature The signature.
     * @param hash The hash.
     * @return True if the decrypted signature matches the hash, false otherwise.
     */
    public static boolean verify(String signature, String hash) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
            String decoded = new String(cipher.doFinal(Base64.getDecoder().decode(signature)));

            return decoded.equals(hash);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
