import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

/**
 * Admin Password Reset Utility
 * 
 * This tool allows you to reset an admin user's password securely.
 * It uses SHA-256 with salt (same algorithm as the application).
 * 
 * Usage:
 *   1. Compile: javac ResetAdminPassword.java
 *   2. Run: java ResetAdminPassword
 *   3. Enter your new password
 *   4. Copy the hash provided
 *   5. Update your database with: 
 *      UPDATE admin_users SET password_hash = '<HASH>' WHERE username = 'admin';
 */
public class ResetAdminPassword {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║    ADMIN PASSWORD RESET UTILITY - CyberNova       ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        // Get the admin username
        System.out.print("Enter admin username (default: admin): ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            username = "admin";
        }

        // Get the new password
        System.out.print("Enter your NEW password: ");
        String newPassword = scanner.nextLine();

        if (newPassword.isEmpty()) {
            System.out.println("❌ Password cannot be empty!");
            scanner.close();
            return;
        }

        // Confirm password
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Passwords do not match!");
            scanner.close();
            return;
        }

        scanner.close();

        // Generate the hash
        System.out.println();
        System.out.println("🔐 Generating secure password hash...");
        System.out.println();

        try {
            String passwordHash = hashPassword(newPassword);

            System.out.println("✅ Password hash generated successfully!");
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════╗");
            System.out.println("║              PASSWORD RESET COMMANDS               ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println();

            // Option 1: SQL Command
            System.out.println("📋 OPTION 1: MySQL Update Command");
            System.out.println("─────────────────────────────────────────────────────");
            System.out.println();
            System.out.println("UPDATE admin_users SET password_hash = '" 
                + passwordHash 
                + "' WHERE username = '" + username + "';");
            System.out.println();

            // Option 2: Full steps
            System.out.println("📋 OPTION 2: Manual Steps");
            System.out.println("─────────────────────────────────────────────────────");
            System.out.println();
            System.out.println("1. Open MySQL command prompt or phpMyAdmin");
            System.out.println("2. Select database: USE cybernova_db;");
            System.out.println("3. Run the command above");
            System.out.println("4. Or use: phpMyAdmin → cybernova_db → admin_users");
            System.out.println();

            // Option 3: Via Windows batch
            System.out.println("📋 OPTION 3: Via Windows Command Line");
            System.out.println("─────────────────────────────────────────────────────");
            System.out.println();
            System.out.println("mysql -h localhost -u root -pPASSWORD@123 cybernova_db");
            System.out.println();
            System.out.println("Then paste this command:");
            System.out.println("UPDATE admin_users SET password_hash = '" 
                + passwordHash 
                + "' WHERE username = '" + username + "';");
            System.out.println();

            System.out.println("╔════════════════════════════════════════════════════╗");
            System.out.println("║              PASSWORD RESET COMPLETE!              ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("✅ After updating the database:");
            System.out.println("   1. Go to: http://localhost:8080/pd_webapp/admin/login");
            System.out.println("   2. Username: " + username);
            System.out.println("   3. Password: " + newPassword);
            System.out.println("   4. Click Login");
            System.out.println();

        } catch (Exception e) {
            System.out.println("❌ Error generating password hash!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Hash a password with a random salt
     * Format: Base64(salt)$Base64(hash)
     */
    public static String hashPassword(String plainPassword) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);
            String hashString = hashWithSalt(plainPassword, salt);
            return saltString + "$" + hashString;
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    /**
     * Hash password with salt using SHA-256
     */
    private static String hashWithSalt(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}

