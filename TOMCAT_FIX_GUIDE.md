# Tomcat Deployment Fix & Database Connection Testing

## ✅ STEP 1: Test Database Connection

I've created a database connection test file: `DBConnectionTest.java`

### To Run the Test:

**Option A - Via IntelliJ IDE:**
1. Open `src/main/java/com/taskproject/pd_webapp/util/DBConnectionTest.java`
2. Right-click on the file → **Run 'DBConnectionTest.main()'**
3. Check the console output for connection results

**Option B - Via Command Line:**
```powershell
cd C:\Users\ADMIN\AppData\Local\PD_WEBAPP
mvn clean compile exec:java -Dexec.mainClass="com.taskproject.pd_webapp.util.DBConnectionTest"
```

---

## ❌ STEP 2: Fix Tomcat Deployment Error

The error **"Configuration Error: deployment source 'PD_WEBAPP:war exploded' is not valid"** occurs when:
- The artifact configuration is corrupted
- Build output is missing
- Project needs to be rebuilt

### Solution:

**Step 1: Clean and Rebuild Project**
```powershell
# In IntelliJ Terminal:
mvn clean
mvn install -DskipTests
```

Or use IntelliJ menu:
- **Build** → **Clean Project**
- **Build** → **Rebuild Project**

**Step 2: Reconfigure Tomcat Run Configuration**

1. Go to **Run** → **Edit Configurations**
2. Find "Tomcat 11.0.1" (or similar name)
3. In the **Deployment** tab:
   - Remove the artifact if it shows red ❌
   - Click **+** button → Select **Artifact**
   - Choose: `pd_webapp:war exploded`
   - Set **Application context**: `/pd_webapp`

4. In the **Server** tab:
   - Set **Server**: Apache Tomcat 11.0.1
   - Set **Port**: 8080
   - Check: **Use default server settings**

5. Click **OK**

**Step 3: Rebuild the Project**
- **Build** → **Build Project**

**Step 4: Run Tomcat**
- Click the **Run** button or press **Shift+F10**

---

## 📊 Expected Output

### Successful Database Connection Test Output:
```
========================================
CyberNova Analytics - Database Connection Test
========================================

[1] Attempting to connect to database...
✅ CONNECTION SUCCESSFUL!

[2] Database Information:
    - Driver Name: MySQL Connector/J
    - Driver Version: 8.0.33
    - Database Product Name: MySQL
    - Database Product Version: 8.0.xx
    - URL: jdbc:mysql://localhost:3306/cybernova_db
    - Username: root

[3] Database Tables:
    ✓ admin_users
    ✓ clients
    ✓ security_requests
    ✓ technical_problems
    ✓ cyber_blog_articles
    ✓ case_studies
    ✓ testimonials
    ✓ workshop_events
    ✓ workshop_gallery
    ✓ solutions
    ✓ request_analytics
    ✓ request_type_analytics
    ✓ regional_demand
    
    Total tables found: 13

========================================
✅ ALL TESTS PASSED!
========================================
```

### Successful Tomcat Deployment Output:
```
Connected to server
[timestamp] Artifact pd_webapp:war exploded: Artifact is being deployed, please wait…
[timestamp] Artifact pd_webapp:war exploded: Deploy took X,XXX milliseconds
```

---

## 🔧 Troubleshooting

### If Database Connection Test Fails:

**Error: "Unable to connect to MySQL database"**
- ✓ Check MySQL is running: Open MySQL Workbench
- ✓ Verify database exists: `SHOW DATABASES;` (should show `cybernova_db`)
- ✓ Check credentials: username=`root`, password=`PASSWORD@123`
- ✓ Verify port: MySQL should be on `localhost:3306`

**Error: "MySQL JDBC Driver not found"**
- Run: `mvn dependency:resolve`
- Then clean and rebuild

### If Tomcat Still Won't Deploy:

1. **Clear Tomcat Cache**:
   - Delete: `C:\Users\ADMIN\AppData\Local\JetBrains\IntelliJIdea2024.3\tomcat\*\work\*`

2. **Invalidate IDE Cache**:
   - **File** → **Invalidate Caches** → **Clear cached files**

3. **Recreate Run Configuration**:
   - Delete existing "Tomcat 11.0.1" configuration
   - Create new one from scratch via **Run** → **Edit Configurations**

---

## ✅ Testing the Application

Once Tomcat starts successfully:

1. Open browser: `http://localhost:8080/pd_webapp/home`
2. You should see the CyberNova Analytics homepage
3. Test the security request form at `/pd_webapp/contact`

---

## 📋 Quick Checklist

- [ ] Database SQL script executed successfully
- [ ] MySQL database `cybernova_db` created with 13 tables
- [ ] Database connection test passes
- [ ] Project cleaned and rebuilt
- [ ] Tomcat run configuration reconfigured
- [ ] Project rebuilt again
- [ ] Tomcat starts without errors
- [ ] Application accessible at http://localhost:8080/pd_webapp/home

