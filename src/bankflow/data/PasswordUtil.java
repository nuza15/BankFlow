package bankflow.data;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Salted PBKDF2 password hashing (JDK built-in, no external library). */
public final class PasswordUtil {
    private static final SecureRandom RNG = new SecureRandom();
    private static final int ITERATIONS = 120_000;

    private PasswordUtil() { }

    public static byte[] newSalt() {
        byte[] salt = new byte[16];
        RNG.nextBytes(salt);
        return salt;
    }

    public static String hash(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 256);
            byte[] h = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(h);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Password hashing unavailable", e);
        }
    }

    public static boolean verify(String password, byte[] salt, String expected) {
        return MessageDigest.isEqual(hash(password, salt).getBytes(StandardCharsets.UTF_8),
                expected.getBytes(StandardCharsets.UTF_8));
    }
}
