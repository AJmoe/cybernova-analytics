# Step-by-Step Setup Guide

## Overview

```
Step 1: Create MySQL Database (2 min)
     ↓
Step 2: Configure Credentials if needed (5 min)
     ↓
Step 3: Build Project (5 min)
     ↓
Step 4: Deploy WAR (3 min)
     ↓
Step 5: Test Workflow (10 min)
     ↓
✅ Complete!
```

---

## STEP 1: Create MySQL Database

### Using MySQL Workbench (Recommended)

1. Open MySQL Workbench
2. File → Open SQL Script
3. Select: `C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql`
4. Click the lightning bolt icon (⚡) to execute
5. Wait for completion

### Using MySQL CLI

```powershell
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"
mysql -u root -p < scripts\mysql-setup.sql
# Enter your MySQL password when prompted
```

### Verify Success

```sql
-- In MySQL, run this to verify:
SHOW DATABASES LIKE 'cybernova%';
USE cybernova_analytics;
SHOW TABLES;
SELECT COUNT(*) FROM security_requests;  -- Should show: 5
```

---

## STEP 2: Update Database Credentials (If Needed)

File: `src/main/java/com/taskproject/pd_webapp/util/DBConnection.java`

**Default config (no changes needed if):**
- MySQL on localhost:3306
- User: root
- No password

**Change these lines if different:**

```java
private static final String DB_HOST = "localhost";      // Your MySQL host
private static final int DB_PORT = 3306;                // Your MySQL port
private static final String DB_USER = "root";           // Your MySQL user
private static final String DB_PASSWORD = "";           // Your MySQL password
```

Examples:
```java
// If MySQL has password "mypass123":
private static final String DB_PASSWORD = "mypass123";

// If MySQL on remote server:
private static final String DB_HOST = "192.168.1.100";

// If MySQL on custom port 3307:
private static final int DB_PORT = 3307;
```

Save the file after changes.

---

## STEP 3: Build Project

```powershell
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"

# Option A: Clean and build
mvn clean install

# Option B: Just build (faster if already built)
mvn install

# Option C: Force update
mvn clean install -U
```

**Success indicator:** Look for `[INFO] BUILD SUCCESS` at the end

**Output location:** `target/pd_webapp.war`

---

## STEP 4: Deploy WAR File

### For Apache Tomcat

```powershell
# Copy WAR file to Tomcat
Copy-Item "target\pd_webapp.war" "C:\Apache Tomcat\webapps\" -Force

# Start Tomcat (if not already running)
& "C:\Apache Tomcat\bin\startup.bat"

# Or if using Windows service:
# Services → Find Apache Tomcat → Right-click → Start
```

### For Jetty

```powershell
Copy-Item "target\pd_webapp.war" "C:\path\to\jetty\webapps\" -Force
# Then start Jetty per your setup
```

Wait 10-15 seconds for server to start and deploy the app.

---

## STEP 5: Test the Workflow

### 5.1: Access Login Page

Open browser and go to:
```
http://localhost:8080/pd_webapp/admin/login
```

You should see a login form.

### 5.2: Login

```
Username: admin
Password: admin123

Click: Login button
```

Should redirect to dashboard.

### 5.3: View Dashboard

```
URL: http://localhost:8080/pd_webapp/admin/dashboard
```

You should see:
- Dashboard heading
- Three analytics cards
- Filter bar
- Table with 5 security requests (Amina, Tebogo, Sipho, Naledi, Kabelo)

### 5.4: View Request Details

- Click "View details" button on first request
- Should show request detail page with all fields
- Should show "Update status" dropdown
- Should show status buttons

### 5.5: Update Status

- Change status dropdown from "New" to "In Review"
- Click "Save status"
- Should see green success message
- Status should update

### 5.6: Verify in Database

```sql
-- In MySQL, verify the change persisted:
SELECT id, name, status FROM security_requests WHERE id = 1;
-- Should show status = "In Review"
```

---

## Success Checklist

✅ MySQL database created
✅ Configuration matches your MySQL
✅ Project builds successfully
✅ WAR deployed to server
✅ Can access login page
✅ Can login with admin/admin123
✅ Can see dashboard with 5 requests
✅ Can view request details
✅ Can update status
✅ Status change persists in database

**If all checked: You're done! 🎉**

---

## Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| Can't reach login page | Check if Tomcat is running, check port 8080 |
| Login fails | Verify admin user exists in admin_users table |
| No requests shown | Check data inserted: `SELECT COUNT(*) FROM security_requests;` |
| Status won't save | Check MySQL is running, check database credentials |
| Build fails | Run `mvn clean install -U` |
| WAR not deploying | Delete old WAR/folder, restart server |

For more detailed troubleshooting, see `docs/TROUBLESHOOTING.md`

---

## Summary

**Total time:** ~30 minutes

Your request detail workflow is now fully functional with MySQL database integration!

