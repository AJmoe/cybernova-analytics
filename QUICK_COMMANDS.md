# Quick Command Reference

## 1️⃣ Run MySQL Setup (First Time Only)

### Option A: Using MySQL Workbench (GUI)
```
1. Open MySQL Workbench
2. Click "File" → "Open SQL Script"
3. Navigate to: C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql
4. Click "Open"
5. Click the lightning bolt icon (Execute All)
6. Wait for completion ✅
```

### Option B: Using MySQL CLI (Terminal)
```powershell
# Open PowerShell and run:
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"
mysql -u root -p < scripts\mysql-setup.sql

# When prompted: Enter your MySQL password (or press Enter if no password)
```

### Option C: Using MySQL Workbench (Manual)
```
1. Open MySQL Workbench
2. Create new query (File → New Query Tab or Ctrl+Shift+N)
3. Copy entire contents of: scripts/mysql-setup.sql
4. Paste into the SQL editor
5. Select all (Ctrl+A)
6. Execute (Ctrl+Enter or click lightning bolt)
```

---

## 2️⃣ Verify MySQL Setup

After running the script, verify everything was created:

### In MySQL Workbench:
```sql
-- Check database exists
SHOW DATABASES LIKE 'cybernova%';

-- Check tables exist
USE cybernova_analytics;
SHOW TABLES;

-- Check sample data
SELECT COUNT(*) FROM security_requests;
-- Should show: 5

-- Check admin user exists
SELECT * FROM admin_users WHERE username = 'admin';
-- Should show: admin, admin123, admin@cybernova.com
```

### From PowerShell:
```powershell
mysql -u root -p cybernova_analytics -e "SELECT COUNT(*) as RequestCount FROM security_requests;"
mysql -u root -p cybernova_analytics -e "SELECT * FROM admin_users WHERE username='admin';"
```

---

## 3️⃣ Update Database Configuration (If Needed)

### If your MySQL has a password:
```
File: src\main\java\com\taskproject\pd_webapp\util\DBConnection.java

Line 14 - Change from:
    private static final String DB_PASSWORD = "";

To:
    private static final String DB_PASSWORD = "your_password";
```

### If your MySQL is on different host:
```
File: src\main\java\com\taskproject\pd_webapp\util\DBConnection.java

Line 10 - Change from:
    private static final String DB_HOST = "localhost";

To:
    private static final String DB_HOST = "your_host_or_ip";
```

### If your MySQL is on different port:
```
File: src\main\java\com\taskproject\pd_webapp\util\DBConnection.java

Line 11 - Change from:
    private static final int DB_PORT = 3306;

To:
    private static final int DB_PORT = 3307;  // or your port
```

---

## 4️⃣ Build the Project

```powershell
# Navigate to project
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"

# Clean previous build
mvn clean

# Build new version (downloads MySQL driver automatically)
mvn install

# Or in one command
mvn clean install
```

**Expected output:**
```
[INFO] Building pd_webapp 1.0.0-SNAPSHOT
[INFO] --------------------------------[ war ]---------------------------------
...
[INFO] --- maven-war-plugin:3.4.0:war (default-war) @ pd_webapp ---
[INFO] Building war: ...\PD_WEBAPP\target\pd_webapp.war
[INFO] --------------------------------[ SUCCESS ]
```

**Output location:** `target/pd_webapp.war`

---

## 5️⃣ Deploy WAR File

### For Tomcat:
```powershell
# Copy WAR to Tomcat's webapps folder
Copy-Item "target\pd_webapp.war" "C:\path\to\tomcat\webapps\"

# Or using xcopy
xcopy "target\pd_webapp.war" "C:\Apache Tomcat\webapps\" /Y
```

### For Jetty:
```powershell
# Copy to Jetty webapps
Copy-Item "target\pd_webapp.war" "C:\path\to\jetty\webapps\"
```

### Using IDE (IntelliJ/VS Code):
```
1. Right-click project → Run on Server
2. Or: Configure run configuration → Deploy WAR
3. Select local Tomcat/Jetty
4. Start server
```

---

## 6️⃣ Test the Workflow

### Open in Browser:
```
http://localhost:8080/pd_webapp/admin/login
```

### Login:
```
Username: admin
Password: admin123
```

### View Dashboard:
```
http://localhost:8080/pd_webapp/admin/dashboard
```

### Click on first request's "View details" button
```
Should see request detail page with all fields populated
```

### Update Status:
```
1. Change status dropdown from "New" to "In Review"
2. Click "Save status" button
3. Should see: "Status updated successfully" message
4. Status should be updated in database
```

### Verify in Database:
```sql
SELECT id, name, status FROM security_requests WHERE id = 1;
-- Should show: 1, Amina, In Review
```

---

## 7️⃣ Troubleshooting Commands

### Check if MySQL is running:
```powershell
# Windows - Check services
Get-Service -Name MySQL80

# Try to connect
mysql -u root -h localhost

# If fails: Start MySQL
net start MySQL80
```

### Check if port 3306 is open:
```powershell
netstat -ano | findstr :3306
```

### Check Maven version:
```powershell
mvn --version
```

### Check Java version:
```powershell
java -version
```

### Check what's in WAR file:
```powershell
# List files in WAR
jar tvf target\pd_webapp.war | findstr mysql-connector

# Should find: WEB-INF/lib/mysql-connector-java-8.0.33.jar
```

### View application logs:
```powershell
# Tomcat logs
Get-Content "C:\Apache Tomcat\logs\catalina.out" -Tail 50

# Jetty logs (if enabled)
Get-Content "logs\yyyy_mm_dd.request.log" -Tail 50
```

---

## 8️⃣ Common Issues & Quick Fixes

### Issue: "Unknown database"
```powershell
# Run setup script again
mysql -u root -p < scripts\mysql-setup.sql
```

### Issue: "Access denied for user"
```
Edit: src\main\java\com\taskproject\pd_webapp\util\DBConnection.java

Line 13-14:
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

Update to match your MySQL credentials
```

### Issue: MySQL service not running
```powershell
# Windows - Start service
net start MySQL80

# Or use Services GUI
services.msc
# Find MySQL80 → Right-click → Start
```

### Issue: WAR deployment not working
```powershell
# Clean Tomcat work directory
rm "C:\Apache Tomcat\work\Catalina\localhost\pd_webapp" -Recurse

# Redeploy WAR
Copy-Item "target\pd_webapp.war" "C:\Apache Tomcat\webapps\" -Force

# Restart Tomcat
```

### Issue: Build fails with "plugin not found"
```powershell
# Update Maven
mvn -U clean install
```

---

## 9️⃣ After Deployment - Success Check

✅ **All of these should work:**

```
1. Browser access: http://localhost:8080/pd_webapp/admin/login
2. Login with: admin / admin123
3. Dashboard URL: http://localhost:8080/pd_webapp/admin/dashboard
4. See 5 security requests listed
5. Click "View details" on any request
6. See request detail page with all fields
7. Change status and click "Save status"
8. See success message
9. Refresh page and verify status changed
10. Check database to confirm status persisted

If all 10 pass → ✅ You're good to go!
```

---

## 🔟 Quick Status Check Commands

### In PowerShell:
```powershell
# Check MySQL connection
$result = mysql -u root -p -e "SELECT 1;" 2>&1
if ($result -match "1") { Write-Host "✅ MySQL Connected" } else { Write-Host "❌ MySQL Failed" }

# Check database
mysql -u root -p cybernova_analytics -e "SELECT COUNT(*) FROM security_requests;" 2>&1

# Check admin user
mysql -u root -p cybernova_analytics -e "SELECT username FROM admin_users LIMIT 1;" 2>&1

# Build project
mvn clean install 2>&1 | Select-String "SUCCESS", "FAILURE"
```

### In MySQL Workbench:
```sql
-- Quick verification script
USE cybernova_analytics;
SELECT 
  (SELECT COUNT(*) FROM security_requests) as Requests,
  (SELECT COUNT(*) FROM admin_users) as AdminUsers,
  (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'cybernova_analytics') as TableCount;
```

Expected result:
```
Requests: 5
AdminUsers: 1
TableCount: 6
```

---

## 📋 Setup Checklist (Tick as you go)

- [ ] Open PowerShell in project directory
- [ ] Run MySQL setup script (mysql-setup.sql)
- [ ] Verify MySQL database created
- [ ] Update DB credentials if needed (DBConnection.java)
- [ ] Build project (mvn clean install)
- [ ] Verify pd_webapp.war created
- [ ] Deploy WAR to Tomcat/Jetty
- [ ] Start application server
- [ ] Open login page in browser
- [ ] Login with admin/admin123
- [ ] View dashboard - see 5 requests
- [ ] Click "View details" on request
- [ ] Update status
- [ ] See success message
- [ ] Verify in MySQL database
- [ ] ✅ **ALL DONE!**

---

## 📞 If Something Goes Wrong

**Most common solutions:**

1. **Database not found?**
   ```powershell
   mysql -u root -p < scripts\mysql-setup.sql
   ```

2. **Connection refused?**
   ```powershell
   net start MySQL80
   ```

3. **Wrong password?**
   ```
   Edit: src\main\java\com\taskproject\pd_webapp\util\DBConnection.java
   Line 14: Update DB_PASSWORD
   ```

4. **Build failed?**
   ```powershell
   mvn clean install -U
   ```

5. **Page not found?**
   - Check URL: http://localhost:8080/pd_webapp/admin/login
   - Check application server is running
   - Check logs for errors

---

**You got this! 🚀**

Follow these commands in order and everything will work perfectly!

