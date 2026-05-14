import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
public class GenerateHash {
    public static void main(String[] args) throws Exception {
        String password = "password2026";
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        String hashString = Base64.getEncoder().encodeToString(hashedPassword);
        String finalHash = saltString + "$" + hashString;
         System.out.println("Password: password2026");
        System.out.println("Generated Hash: " + finalHash);
    }
}
