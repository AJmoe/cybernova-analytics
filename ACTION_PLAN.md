# 🚀 IMMEDIATE ACTION PLAN - Tomcat & Database Setup

## Current Status
✅ Database schema created (cybernova_db)  
✅ Java models synchronized  
✅ DBConnection.java configured  
❌ Tomcat deployment failing  
❓ Database connection needs testing  

---

## IMMEDIATE ACTIONS (Follow in Order)

### ACTION 1: Fix Tomcat Artifact Configuration (5 minutes)

1. **Open IntelliJ IDEA**
2. Go to **Run** → **Edit Configurations**
3. Look for "Tomcat 11.0.1" or similar - if it has a RED X mark, delete it
4. Click **+** (Add New Configuration)
5. Select **Tomcat Server** → **Local**
6. Configure as follows:

   | Setting | Value |
   |---------|-------|
   | Name | Tomcat 11.0.1 |
   | Server | Apache Tomcat 11.0.1 |
   | Port | 8080 |
   | Tomcat Home | C:\Program Files\Apache Software Foundation\Tomcat 11.0_TomcatX |
   | VM options | (leave empty) |

7. **Deployment Tab:**
   - Click **+** → **Artifact**
   - Select `pd_webapp:war exploded`
   - Set **Application context** to `/pd_webapp`
   - Click **OK** to save

---

### ACTION 2: Clean Build (3 minutes)

In IntelliJ:

1. **Build** → **Clean Project** (wait for completion)
2. **Build** → **Rebuild Project** (wait for completion)
3. You should see "Build completed successfully"

---

### ACTION 3: Test Database Connection (2 minutes)

1. In IntelliJ, open: `src/main/java/com/taskproject/pd_webapp/util/DBConnectionTest.java`
2. Right-click on the class → **Run 'DBConnectionTest.main()'**
3. Look at the **Run** console for output

**If you see "✅ ALL TESTS PASSED!" → Database is working ✓**
**If you see connection error → Database not running (open MySQL Workbench first)**

---

### ACTION 4: Start Tomcat (1 minute)

1. Click the **Run** button (green triangle) at the top
2. Or press **Shift + F10**
3. Wait for: "Connected to server" + "Deploy took X ms"

If you see "Configuration Error: deployment source..." again:
- Stop Tomcat (red square)
- Repeat ACTION 2 (Clean Rebuild)
- Click Run again

---

### ACTION 5: Verify Application (1 minute)

1. Open browser: `http://localhost:8080/pd_webapp/home`
2. You should see the **CyberNova Analytics** homepage
3. If you see a blank page → check Tomcat logs (bottom of IntelliJ)

---

## 📋 CRITICAL CHECKLIST

Before starting Tomcat, verify:

- [ ] MySQL is running (check MySQL Workbench or Task Manager)
- [ ] Database `cybernova_db` exists (execute SQL script if needed)
- [ ] Tomcat configuration has no RED X marks
- [ ] `pd_webapp:war exploded` is set as artifact
- [ ] Project has been cleaned and rebuilt
- [ ] Application context is `/pd_webapp`

---

## ⚠️ If Problems Persist

### Problem: "Connection refused" (MySQL not running)
```
Solution: Open MySQL Workbench → Click MySQL connection
```

### Problem: "Cannot find database cybernova_db"
```
Solution: 
1. Open MySQL Workbench
2. Open new SQL tab
3. Copy-paste entire contents of: scripts/cybernova-setup-clean.sql
4. Click Execute (lightning bolt)
5. Wait for completion
```

### Problem: "Deploy failed" with RED X
```
Solution:
1. Run: Build → Clean Project
2. Wait for completion
3. Run: Build → Rebuild Project
4. Wait for "Build completed successfully"
5. Try Run again
```

### Problem: Blank page at localhost:8080/pd_webapp
```
Solution:
1. Check Tomcat logs (View → Tool Windows → Tomcat Localhost Log)
2. Look for errors like "Unable to connect to database"
3. If database connection error → Verify MySQL is running
4. Restart Tomcat (red square → wait → green play button)
```

---

## 🎯 SUCCESS INDICATORS

✅ **Database Test Output:**
- Shows 13 tables in cybernova_db
- Shows "Connection Successful"

✅ **Tomcat Logs Show:**
```
Connected to server
Artifact pd_webapp:war exploded: Deploy took XXX milliseconds
```

✅ **Browser Shows:**
- CyberNova Analytics homepage
- Navigation menu visible
- No error messages

---

## 🆘 Need Help?

If you're still stuck:
1. Share the Tomcat log output (View → Tool Windows → Tomcat Localhost Log)
2. Share the Database Test output (Run the DBConnectionTest)
3. Take a screenshot of the Run Configuration dialog

---

**DO THESE 5 ACTIONS IN ORDER - Should take ~15 minutes total!**

