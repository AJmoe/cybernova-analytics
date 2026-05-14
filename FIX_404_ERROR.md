# 🔧 FIX: 404 Error - Wrong Context Path

## Problem
```
HTTP Status 404 – Not Found
The requested resource [/PD_WEBAPP_war_exploded/home] is not available
```

**Why:** The application context is set to `/PD_WEBAPP_war_exploded` but should be `/pd_webapp`

---

## ✅ SOLUTION: Reconfigure Tomcat Context Path

### Step 1: Stop Tomcat
- Click the red **STOP** button or press **Ctrl+F2**
- Wait for server to fully stop

### Step 2: Edit Tomcat Configuration

1. Go to **Run** → **Edit Configurations**
2. Select **Tomcat 11.0.1** from the left list
3. Go to the **Deployment** tab
4. You should see an artifact listed:
   ```
   Artifact: pd_webapp:war exploded
   Application context: /PD_WEBAPP_war_exploded  ❌ WRONG
   ```

5. **CHANGE THE APPLICATION CONTEXT:**
   - Click in the "Application context" field
   - Clear it completely
   - Type: `/pd_webapp`  ✅ CORRECT
   - Press Enter

6. Click **OK** to save

### Step 3: Rebuild Project

1. **Build** → **Clean Project**
2. **Build** → **Rebuild Project**
3. Wait for "Build completed successfully"

### Step 4: Start Tomcat

- Click the green **RUN** button
- Wait for: "Connected to server" + "Deploy took X ms"

### Step 5: Test in Browser

Open: **http://localhost:8080/pd_webapp/home**

You should see:
✅ CyberNova Analytics homepage  
✅ Navigation menu  
✅ No 404 errors  

---

## 📋 Quick Reference

| Setting | Value |
|---------|-------|
| **Artifact** | `pd_webapp:war exploded` |
| **Application context** | `/pd_webapp` |
| **Port** | 8080 |
| **URL** | http://localhost:8080/pd_webapp/home |

---

## 🎯 If It Still Doesn't Work

Try this "nuclear option":

1. **Stop Tomcat**
2. **Delete Tomcat configuration completely:**
   - Run → Edit Configurations → Select "Tomcat 11.0.1" → Click **-** (minus button)
3. **Create NEW configuration from scratch:**
   - Run → Edit Configurations → Click **+**
   - Choose "Tomcat Server" → "Local"
   - Fill in:
     - Name: `Tomcat 11.0.1`
     - Server: `Apache Tomcat 11.0.1`
     - Startup script: (auto-populated)
   - **Deployment tab:**
     - Click **+** → Select Artifact
     - Choose `pd_webapp:war exploded`
     - Set context: `/pd_webapp`
   - **Startup/Connection tab:**
     - URL: `http://localhost:8080/pd_webapp`
   - Click **OK**

4. **Clean → Rebuild → Run**

This should definitely fix it! 🚀

