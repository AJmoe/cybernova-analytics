# Adding New Admin Users - Password Hash Generation

## How to Create New Admin Users

### Option 1: Use the Utility Class (RECOMMENDED)

The `AdminUserDAO` class provides a static method to generate password hashes:

```java
import com.taskproject.pd_webapp.dao.AdminUserDAO;

public class CreateAdminUser {
    public static void main(String[] args) {
        // Generate hash for a new password
        String password = "MySecurePassword123";
        String hash = AdminUserDAO.hashPassword(password);
        
        System.out.println("Username: admin2");
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        System.out.println();
        System.out.println("Use this SQL:");
        System.out.println("INSERT INTO admin_users (username, password_hash, email, full_name, is_active)");
        System.out.println("VALUES ('admin2', '" + hash + "', 'admin2@cybernova.com', 'Second Admin', TRUE);");
    }
}
```

### Option 2: Use Pre-Generated Hashes

Available pre-generated hashes for password "admin123":

```
Hash 1 (Use This):
ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=

Password: admin123
```

### Option 3: Inline Code

If you just need the hash quickly, use this Java snippet:

```java
java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
java.security.SecureRandom random = new java.security.SecureRandom();
byte[] salt = new byte[16];
random.nextBytes(salt);

String saltString = java.util.Base64.getEncoder().encodeToString(salt);
md.update(salt);
byte[] hashedPassword = md.digest("yourPassword".getBytes());
String hashString = java.util.Base64.getEncoder().encodeToString(hashedPassword);
String finalHash = saltString + "$" + hashString;

System.out.println(finalHash);
```

---

## SQL Templates for Adding Users

### Add New Admin User

```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES (
    'admin2',
    '[PASTE_GENERATED_HASH_HERE]',
    'admin2@cybernova.com',
    'Second Administrator',
    TRUE
);
```

### Add Multiple Users

```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES 
('admin', 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=', 'admin@cybernova.com', 'Primary Admin', TRUE),
('analyst1', '[HASH_FOR_ANALYST1]', 'analyst1@cybernova.com', 'Senior Analyst', TRUE),
('analyst2', '[HASH_FOR_ANALYST2]', 'analyst2@cybernova.com', 'Junior Analyst', TRUE);
```

### Update Password for Existing User

```sql
UPDATE admin_users 
SET password_hash = '[NEW_HASH]'
WHERE username = 'admin';
```

### Disable a User Account

```sql
UPDATE admin_users 
SET is_active = FALSE
WHERE username = 'admin2';
```

### Delete a User

```sql
DELETE FROM admin_users 
WHERE username = 'admin2';
```

---

## Verify New Users

```sql
-- Check all admin users
SELECT admin_id, username, email, full_name, is_active, created_at
FROM admin_users
ORDER BY created_at DESC;

-- Check specific user
SELECT * FROM admin_users WHERE username = 'admin2';

-- Count total admins
SELECT COUNT(*) as total_admins FROM admin_users WHERE is_active = TRUE;
```

---

## Hash Format Explained

Each hash has the format: `[SALT]$[HASH]`

Example:
```
ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=
|___________________|  |___________________________________|
    Base64 Salt               Base64 SHA-256 Hash
```

### How It Works:
1. Generate random 16-byte salt
2. Apply salt to the password using SHA-256
3. Encode both salt and hash using Base64
4. Store as: `[ENCODED_SALT]$[ENCODED_HASH]`

### During Login:
1. Extract salt from stored hash (before the `$`)
2. Use extracted salt to hash the provided password
3. Compare the computed hash with stored hash
4. If match → Login successful ✓

---

## Security Best Practices

✅ Always use this utility to generate hashes  
✅ Never store plain text passwords  
✅ Use strong passwords (12+ characters, mixed case, numbers, symbols)  
✅ Regenerate hashes when needed  
✅ Disable accounts instead of deleting them  
✅ Audit password changes regularly  

---

## Example: Creating 3 New Admin Users

### Step 1: Generate hashes for each password

```
User 1: analyst_john / SecurePass123!@ 
  → [Run hashPassword to get hash1]

User 2: analyst_jane / AnotherPass789$#
  → [Run hashPassword to get hash2]

User 3: admin_backup / BackupAdmin456!
  → [Run hashPassword to get hash3]
```

### Step 2: Run SQL

```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES 
('analyst_john', 'hash1', 'john@cybernova.com', 'John Analyst', TRUE),
('analyst_jane', 'hash2', 'jane@cybernova.com', 'Jane Analyst', TRUE),
('admin_backup', 'hash3', 'backup@cybernova.com', 'Backup Admin', TRUE);
```

### Step 3: Verify

```sql
SELECT username, email, full_name, is_active FROM admin_users ORDER BY created_at DESC LIMIT 3;
```

---

## Troubleshooting

### "Invalid username or password" after adding new user
- Verify the hash was copied correctly
- Check that email is unique
- Confirm is_active is set to TRUE
- Make sure username is exactly as you entered it

### Different hash generated each time
- This is NORMAL! Each password gets a random salt
- `admin123` will produce different hashes each time
- But the same plain password will verify against any generated hash

### Need to reset a user's password
- Generate new hash for new password
- UPDATE the password_hash column
- User can now login with new password

---

## Pre-Generated Hashes for Testing

**Password:** admin123
**Hash:** ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=

You can use this hash for initial testing and deployment.
For production, always generate new hashes!

---

**Remember:** The hash changes every time you run hashPassword() due to random salt, but each hash will correctly verify the same password!

