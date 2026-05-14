import java.security.SecureRandom;
import java.util.Base64;
public class PasswordHasher {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hash);
    }
    public static String hashPassword(String password) {
        String salt = generateSalt();
        return hashpw(password, salt);
    }
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return BCryptHash.gensalt(12, salt);
    }
    private static String hashpw(String password, String salt) {
        return BCryptHash.hashpw(password, salt);
    }
}
