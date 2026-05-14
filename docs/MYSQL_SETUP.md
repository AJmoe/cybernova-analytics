# MySQL Setup Guide for CyberNova Analytics Ltd

## Overview
The application has been configured to use MySQL instead of the embedded H2 database. Follow these steps to set up your MySQL database.

---

## Prerequisites
- MySQL Server installed and running
- MySQL Workbench or MySQL CLI access
- Port 3306 available (default MySQL port)

---

## Step 1: Create Database and Tables

### Option A: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Open the SQL Editor (File → New Query Tab or Ctrl+Shift+N)
4. Copy the entire content from `scripts/mysql-setup.sql`
5. Paste it into the SQL Editor
6. Execute the script (Ctrl+Enter or click the lightning bolt icon)

### Option B: Using MySQL CLI
```bash
mysql -u root -p < scripts/mysql-setup.sql
```

When prompted, enter your MySQL root password.

---

## Step 2: Update Database Configuration

Edit the following file to match your MySQL credentials:
**File**: `src/main/java/com/taskproject/pd_webapp/util/DBConnection.java`

Update these constants if needed:
```java
private static final String DB_HOST = "localhost";      // Your MySQL server host
private static final int DB_PORT = 3306;                // Your MySQL server port
private static final String DB_NAME = "cybernova_analytics";  // Database name
private static final String DB_USER = "root";           // MySQL username
private static final String DB_PASSWORD = "";           // MySQL password (update if needed)
```

**Example**: If your MySQL password is `mypassword123`:
```java
private static final String DB_PASSWORD = "mypassword123";
```

---

## Step 3: Verify Maven Dependencies

The `pom.xml` has been updated to include the MySQL JDBC driver:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

Maven will automatically download this dependency when you build the project.

---

## Step 4: Verify Database Connection

After deploying the application to your servlet container (Tomcat/Jetty):

1. Access the application at `http://localhost:8080/pd_webapp` (or your configured port)
2. Navigate to `/admin/login`
3. Login with credentials (from the seed data):
   - Username: `admin`
   - Password: `admin123`

If the connection fails, you'll see an error message indicating:
- The database connection issue
- Required configuration steps
- The specific error from MySQL

---

## Database Schema

### Tables Created:

#### 1. **security_requests**
Stores submitted security request forms
- Indexed on: `status`, `issue_type`, `country`, `created_at`
- Includes sample seed data (5 records)

#### 2. **admin_users**
Stores admin credentials (currently: admin/admin123)

#### 3. **case_studies**
For case study content management

#### 4. **cyber_blog_articles**
For cybersecurity blog posts

#### 5. **testimonials**
For client testimonials

#### 6. **workshop_gallery**
For workshop and event gallery items

---

## Troubleshooting

### Connection Error: "Unknown database"
- Ensure the `cybernova_analytics` database was created
- Check script execution completed successfully

### Connection Error: "Access denied for user"
- Verify MySQL username and password in `DBConnection.java`
- Ensure the MySQL user has permissions on `cybernova_analytics` database

### Connection Error: "Can't connect to MySQL server"
- Verify MySQL service is running
- Check MySQL is listening on the correct host/port
- Verify firewall isn't blocking port 3306

### Connection Error: "No suitable driver found"
- Maven dependency wasn't downloaded
- Rebuild with: `mvn clean install`
- Ensure MySQL JDBC driver is in your application's lib folder

---

## Authentication Security

The admin endpoint `/admin/request` is protected by `AdminAuthFilter`:
- All requests to `/admin/*` require an active session with `adminAuthenticated = true`
- Unauthenticated users are redirected to `/admin/login`
- Session timeout is set to 30 minutes (configured in `web.xml`)

**Current admin credentials** (should be changed in production):
- Username: `admin`
- Password: `admin123`

To change the password:
1. Update the hash in the database:
   ```sql
   UPDATE admin_users SET password_hash = 'your_new_password' WHERE username = 'admin';
   ```
2. Implement proper password hashing (bcrypt recommended) for production

---

## Next Steps

1. ✅ MySQL database created
2. ✅ Tables and seed data loaded
3. ✅ Application configured to use MySQL
4. ✅ Authentication protection verified for `/admin/request` endpoint
5. 🔄 **TODO**: Deploy the WAR file to your servlet container
6. 🔄 **TODO**: Test the request detail workflow by:
   - Logging in as admin
   - Viewing the dashboard
   - Clicking "View details" on a request
   - Updating the status
   - Verifying the change persists in MySQL

---

## Notes

- The application uses MySQL mode in compatibility settings
- Unicode (UTF-8) support is enabled for internationalization
- All tables include timestamps for audit trailing
- Foreign keys can be added later if needed for data integrity

For questions or issues, check the application logs for detailed error messages.

