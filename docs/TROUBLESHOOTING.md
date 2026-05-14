# MySQL Troubleshooting Guide

## Problem: "Unknown database 'cybernova_analytics'"

**Symptom:** Application fails to start with error about unknown database

**Solution:**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Run this script:
   ```sql
   CREATE DATABASE IF NOT EXISTS cybernova_analytics;
   USE cybernova_analytics;
   ```
4. Then execute the full `scripts/mysql-setup.sql` file

**Check:** Verify database was created
```sql
SHOW DATABASES LIKE 'cybernova%';
```
Should show: `cybernova_analytics`

---

## Problem: "Table 'security_requests' doesn't exist"

**Symptom:** Error mentions missing security_requests table

**Solution:**
1. Ensure you're in the correct database:
   ```sql
   USE cybernova_analytics;
   ```
2. Run the table creation:
   ```sql
   -- Copy entire scripts/mysql-setup.sql and execute
   ```

**Check:** Verify table was created
```sql
SHOW TABLES;
```
Should show: `security_requests` and 5 other tables

---

## Problem: "Access denied for user 'root'@'localhost'"

**Symptom:** MySQL authentication error - wrong password or user

**Solution:**

### A. Check current MySQL configuration
Edit: `src/main/java/com/taskproject/pd_webapp/util/DBConnection.java`

Current settings:
```java
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";  // Empty password
```

### B. Update if your MySQL is different

**Scenario 1: MySQL has a password**
```java
private static final String DB_PASSWORD = "your_password_here";
```

**Scenario 2: Using different username**
```java
private static final String DB_USER = "myuser";
```

**Scenario 3: Using different host/port**
```java
private static final String DB_HOST = "192.168.1.100";  // IP address
private static final int DB_PORT = 3307;                // Custom port
```

### C. Grant permissions
If database exists but user doesn't have permissions:
```sql
-- Login as root with full permissions
GRANT ALL PRIVILEGES ON cybernova_analytics.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### D. Test the connection
Open MySQL CLI:
```bash
mysql -u root -h localhost -P 3306 cybernova_analytics
```
- If password needed: `mysql -u root -p -h localhost -P 3306 cybernova_analytics`
- Then enter your password when prompted

---

## Problem: "Can't connect to MySQL server on 'localhost:3306'"

**Symptom:** MySQL connection timeout - server not running or wrong host/port

**Solution:**

### Step 1: Verify MySQL is running

**Windows:**
- Open Task Manager → Services tab → Look for "MySQL80" or similar
- Or: Open Services app → Find MySQL → Check if "Running"
- Or: Start MySQL Service:
  ```bash
  net start MySQL80
  ```

**Linux:**
```bash
sudo systemctl status mysql
sudo systemctl start mysql
```

**macOS:**
```bash
mysql.server status
mysql.server start
```

### Step 2: Verify host and port

Test connection from CLI:
```bash
# Default port 3306
mysql -u root -h localhost

# Custom port (e.g., 3307)
mysql -u root -h localhost --port=3307
```

If connection works in CLI but not in app, update `DBConnection.java`:
```java
private static final String DB_HOST = "localhost";  // or "127.0.0.1"
private static final int DB_PORT = 3306;            // Match your MySQL port
```

### Step 3: Check firewall
If using remote MySQL, ensure:
- Port 3306 is open in firewall
- MySQL bind_address allows external connections
- Network connectivity between app and MySQL server

---

## Problem: "MySQL JDBC Driver not available"

**Symptom:** ClassNotFoundException for com.mysql.cj.jdbc.Driver

**Solution:**

### Step 1: Rebuild the project
```bash
mvn clean install
```

This downloads the MySQL connector from Maven:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Step 2: Verify pom.xml
Check `pom.xml` has the MySQL dependency (added already):
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
    <scope>runtime</scope>
</dependency>
```

### Step 3: Verify WAR file
After building, check the WAR contains MySQL driver:
```bash
# Extract and check
unzip -l target/pd_webapp.war | grep mysql-connector
```

Should show: `WEB-INF/lib/mysql-connector-java-8.0.33.jar`

---

## Problem: "No such file or directory: scripts/mysql-setup.sql"

**Symptom:** Can't find the MySQL setup script

**Solution:**

The script is located at:
```
C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql
```

### Option A: Execute from correct directory
```bash
cd C:\Users\ADMIN\AppData\Local\PD_WEBAPP
mysql -u root -p < scripts/mysql-setup.sql
```

### Option B: Absolute path
```bash
mysql -u root -p < C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql
```

### Option C: Copy content manually
1. Open the file in editor
2. Copy all content
3. Paste into MySQL Workbench SQL editor
4. Execute

---

## Problem: "Connection to database is refused"

**Symptom:** Connection refused error - firewall or binding issue

**Solution:**

### Check MySQL is listening on correct interface
```bash
netstat -an | grep 3306
```

Should show something like:
```
LISTENING 127.0.0.1:3306
```

### If only 127.0.0.1 (localhost):
Your MySQL only accepts local connections. That's fine if:
- Application is on same server (localhost)
- Update `DBConnection.java`:
  ```java
  private static final String DB_HOST = "localhost";
  ```

### If using remote MySQL:
Update MySQL config to allow remote:
```sql
-- Allow root from any host
GRANT ALL PRIVILEGES ON cybernova_analytics.* TO 'root'@'%';
FLUSH PRIVILEGES;

-- Edit my.cnf or my.ini
[mysqld]
bind-address = 0.0.0.0  # Listen on all interfaces
```

Then restart MySQL.

---

## Problem: "Incorrect table structure"

**Symptom:** Column names don't match, or column types are wrong

**Solution:**

Check table structure:
```sql
DESCRIBE security_requests;
```

Expected output:
```
Field           | Type      | Null | Key | Default | Extra
id              | bigint    | NO   | PRI | NULL    | auto_increment
name            | varchar   | NO   |     | NULL    |
email           | varchar   | NO   |     | NULL    |
phone           | varchar   | NO   |     | NULL    |
organization    | varchar   | NO   |     | NULL    |
country         | varchar   | NO   |     | NULL    |
job_title       | varchar   | NO   |     | NULL    |
issue_type      | varchar   | NO   |     | NULL    |
description     | longtext  | NO   |     | NULL    |
status          | varchar   | NO   |     | New     |
created_at      | timestamp | NO   |     | NULL    |
updated_at      | timestamp | NO   |     | NULL    |
```

**If columns are missing or wrong:**
1. Backup any existing data if needed
2. Drop and recreate:
   ```sql
   DROP TABLE IF EXISTS security_requests;
   -- Then run the full scripts/mysql-setup.sql
   ```

---

## Problem: Sample data not inserted

**Symptom:** Table exists but no records show in dashboard

**Solution:**

### Check if data exists
```sql
SELECT COUNT(*) as record_count FROM security_requests;
```

If count is 0:
```sql
-- Insert sample data manually
INSERT INTO security_requests (name, email, phone, organization, country, job_title, issue_type, description, status) 
VALUES 
('Amina', 'amina@northwave.bw', '+267 71234567', 'NorthWave Bank', 'Botswana', 'Risk Officer', 'Phishing Response', 'Urgent phishing campaign targeting staff mailbox access.', 'New'),
('Tebogo', 'tebogo@metrocare.co.bw', '+267 72123456', 'MetroCare Clinics', 'Botswana', 'IT Manager', 'Vulnerability Assessment', 'Need a technical scan for exposed services and patch priorities.', 'New'),
('Sipho', 'sipho@logisafrica.za', '+27 821234567', 'Logis Africa', 'South Africa', 'Security Lead', 'Incident Response', 'Suspicious lateral movement detected on warehouse endpoints.', 'In Review'),
('Naledi', 'naledi@financehub.na', '+264 811234567', 'Finance Hub Namibia', 'Namibia', 'Compliance Manager', 'Security Awareness Training', 'Requesting staff training plus policy review after recent audit findings.', 'New'),
('Kabelo', 'kabelo@publicworks.gov.bw', '+267 75432100', 'Ministry Project Office', 'Botswana', 'Director', 'Cloud Hardening', 'Need review of cloud account controls and privileged access configuration.', 'New');
```

Verify:
```sql
SELECT id, name, issue_type, status FROM security_requests;
```

---

## Problem: "Login credentials not working"

**Symptom:** Admin/admin123 doesn't login

**Solution:**

### Check admin user exists
```sql
SELECT * FROM admin_users WHERE username = 'admin';
```

If no result, insert the admin user:
```sql
INSERT INTO admin_users (username, password_hash, email) 
VALUES ('admin', 'admin123', 'admin@cybernova.com');
```

If exists but password wrong:
```sql
UPDATE admin_users SET password_hash = 'admin123' WHERE username = 'admin';
```

**Note:** In production, use proper password hashing (bcrypt):
```java
// Example with bcrypt (add dependency first)
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("admin123");
// Store hashedPassword in database
// On login: encoder.matches(providedPassword, storedHash)
```

---

## Problem: "Status update not saving"

**Symptom:** Can update status in UI but change doesn't persist in database

**Solution:**

### Step 1: Check if update is happening
1. Open MySQL Workbench
2. Select request detail in the app
3. Check dashboard - did status change?

If no change in dashboard:
- Update might be silently failing
- Check application logs for exceptions

### Step 2: Manual update test
In MySQL:
```sql
-- Update directly
UPDATE security_requests SET status = 'In Review' WHERE id = 1;

-- Verify
SELECT id, name, status FROM security_requests WHERE id = 1;
```

If manual update works but app doesn't:
- Check `SecurityRequestRepository.updateStatus()` method
- Check SQL is correct
- Check database user has UPDATE permission

### Step 3: Grant UPDATE permission
```sql
GRANT UPDATE ON cybernova_analytics.security_requests TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

---

## Problem: "Can't find request after update"

**Symptom:** After saving status, request detail page shows 404

**Solution:**

### Check if request still exists
```sql
SELECT * FROM security_requests WHERE id = 1;
```

If record exists: The issue is in the application logic
If record deleted: Don't delete records on update, only update status

### Check for cascade delete
Verify no foreign key constraints causing deletion:
```sql
SHOW CREATE TABLE security_requests\G
```

Should NOT have `ON DELETE CASCADE` for any relationships.

---

## Quick Diagnostic Checklist

Run these commands in order to diagnose issues:

```sql
-- 1. Can you connect to MySQL?
SELECT 1;

-- 2. Does database exist?
SHOW DATABASES LIKE 'cybernova%';

-- 3. Are tables created?
USE cybernova_analytics;
SHOW TABLES;

-- 4. Does security_requests table exist and have data?
SELECT COUNT(*) as record_count FROM security_requests;

-- 5. Can you query a specific request?
SELECT * FROM security_requests WHERE id = 1;

-- 6. Can you update status?
UPDATE security_requests SET status = 'Test' WHERE id = 1;
SELECT status FROM security_requests WHERE id = 1;

-- 7. Does admin user exist?
SELECT * FROM admin_users WHERE username = 'admin';

-- 8. Check indexes are created
SHOW INDEXES FROM security_requests;
```

If all return expected results, the MySQL configuration is correct.

---

## Still Having Issues?

1. **Check Application Logs**
   - Look in Tomcat/Jetty log files for detailed error messages
   - Usually in: `catalina.out` or console output

2. **Enable Debug Mode**
   - Add println statements in `DBConnection.java`
   - Check which step fails

3. **Test Database Directly**
   - All queries above should work in MySQL Workbench
   - If they don't, it's a MySQL setup issue, not application code

4. **Rebuild and Redeploy**
   ```bash
   mvn clean install
   # Redeploy to application server
   ```

5. **Check Configuration**
   - Verify `DBConnection.java` matches your MySQL setup
   - Double-check host, port, database name, username, password

6. **Review Error Message**
   - The error message from `DBConnection.ensureInitialized()` is very helpful
   - It includes the exact next steps needed

---

**Still stuck?** The error message usually tells you exactly what to do. Read it carefully! 🔍

