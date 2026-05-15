# ⚡ Render + Railway Connection: QUICK FIX

## 🔴 Your Problem
```
App trying to connect to: localhost:3306
But database is on: Railway (cloud)
Result: Connection refused ❌
```

## ✅ Your Solution (5 Minutes)

### 1️⃣ Get Railway Connection String
```
Railway Dashboard → MySQL Service → Connect Tab
Copy JDBC string (looks like):
jdbc:mysql://gateway.railway.app:3306/railway?sslMode=REQUIRE
```

### 2️⃣ Set Environment Variables on Render
```
Render Dashboard → Your Service → Environment
Add 3 variables:

DB_URL = jdbc:mysql://gateway.railway.app:3306/railway?sslMode=REQUIRE
DB_USER = root
DB_PASSWORD = [railway-password]

Click SAVE
```

### 3️⃣ Render Redeploys
```
Automatically restarts with new env vars
Takes 1-2 minutes
```

### 4️⃣ Test
```
https://your-app.onrender.com/admin/login
Try to login
Should work now! ✅
```

---

## 🎯 Key Points

✅ Replace `3306` with YOUR Railway port  
✅ Use `?sslMode=REQUIRE` for Railway  
✅ Copy EXACT password from Railway  
✅ Click "Save" on Render (important!)  
✅ Wait for redeploy to finish  

---

## 🔍 Verify It Worked

**Check Render logs:**

Before fix:
```
DB_URL: jdbc:mysql://localhost:3306
Error: Communication link failure
```

After fix:
```
DB_URL: jdbc:mysql://gateway.railway.app:3306/railway
Status: Connected! ✅
```

---

## 📋 Troubleshooting

❌ Still failing?
1. Check env vars on Render (are they there?)
2. Are the values correct? (copy-paste from Railway)
3. Did you click "Save"? (must save!)
4. Wait for redeploy? (check Render logs)
5. Try manual redeploy (in Render dashboard)

---

**That's it!** 3 env vars = Fixed! 🎉

Read: `RENDER_RAILWAY_CONNECTION_FIX.md` for detailed help

