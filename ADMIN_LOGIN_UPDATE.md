# Admin Login Fixed - MySQL Credentials

## Summary
The AdminLoginServlet has been updated to use MySQL database credentials instead of hardcoded values. The system now uses SHA-256 password hashing with salt for secure authentication.

## Changes Made

### 1. **AdminUserDAO.java** (New File)
- Created new Data Access Object class for admin authentication
- Implements `authenticateAdmin()` method that queries the database
- Implements password verification using SHA-256 with salt
- Provides `hashPassword()` utility for generating secure password hashes
- File: `src/main/java/com/taskproject/pd_webapp/dao/AdminUserDAO.java`

### 2. **AdminLoginServlet.java** (Updated)
- Removed hardcoded credentials
- Now uses `AdminUserDAO` for authentication
- Queries `admin_users` table in database
- Stores additional admin info in session (adminId, adminEmail)
- File: `src/main/java/com/taskproject/pd_webapp/web/servlet/AdminLoginServlet.java`

### 3. **pom.xml** (Updated)
- Removed BCrypt dependency (using Java's built-in SHA-256 instead)
- No additional dependencies needed beyond what's already there

## Password Hash Format
System uses: **SHA-256 with 16-byte random salt**
Format: `[Base64_Encoded_Salt]$[Base64_Encoded_Hash]`

## INSERT Command for Admin User

**Add this admin user to your database:**

```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES ('admin', 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=', 'admin@cybernova.com', 'System Administrator', TRUE);
```

**Or if you prefer to replace existing admin:**

```sql
DELETE FROM admin_users WHERE username = 'admin';

INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES ('admin', 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=', 'admin@cybernova.com', 'System Administrator', TRUE);
```

## Login Credentials

- **Username:** admin
- **Password:** admin123

## How It Works

1. **User submits login form** with username and password
2. **AdminLoginServlet.doPost()** receives the request
3. **AdminUserDAO.authenticateAdmin()** is called with username and password
4. Database query retrieves the admin user record
5. Password is verified using the stored salt and hash
6. If match found → User is authenticated and session is created
7. If no match → Error message displayed

## How to Generate New Password Hashes

If you need to create additional admin users or reset a password:

```java
import com.taskproject.pd_webapp.dao.AdminUserDAO;

String plainPassword = "newPassword123";
String hashedPassword = AdminUserDAO.hashPassword(plainPassword);
System.out.println("Use this in the database: " + hashedPassword);
```

Then use the generated hash in your INSERT statement.

## Database Table Structure

The `admin_users` table expected columns:
- `admin_id` - INT AUTO_INCREMENT PRIMARY KEY
- `username` - VARCHAR(100) NOT NULL UNIQUE
- `password_hash` - VARCHAR(255) NOT NULL
- `email` - VARCHAR(120) NOT NULL UNIQUE
- `full_name` - VARCHAR(150)
- `is_active` - BOOLEAN DEFAULT TRUE
- `created_at` - TIMESTAMP DEFAULT CURRENT_TIMESTAMP
- `updated_at` - TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

## Security Features

✓ Passwords are hashed with SHA-256
✓ 16-byte random salt per password
✓ Base64 encoding for safe database storage
✓ Timing-safe password comparison
✓ Only active users can log in
✓ Session timeout after 30 minutes of inactivity
✓ Old sessions invalidated on new login
✓ No plain text passwords stored or transmitted

## Testing

After executing the INSERT command:

1. Start your Tomcat server
2. Navigate to: `http://localhost:8080/pd_webapp/admin/login`
3. Enter username: `admin`
4. Enter password: `admin123`
5. Should redirect to admin dashboard on success

## Files Modified

1. ✓ `src/main/java/com/taskproject/pd_webapp/web/servlet/AdminLoginServlet.java`
2. ✓ `src/main/java/com/taskproject/pd_webapp/dao/AdminUserDAO.java` (NEW)
3. ✓ `pom.xml`

## Next Steps

1. Run the INSERT command on your MySQL database
2. Rebuild the project: `mvn clean package`
3. Deploy to Tomcat
4. Test admin login with credentials: admin / admin123

