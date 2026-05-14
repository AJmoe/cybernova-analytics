# ⚡ Quick Setup Guide - Admin Login Fix

## What You Need to Do

### Step 1: Update Your Database
Run this SQL command in MySQL:

```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active)
VALUES ('admin', 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=', 'admin@cybernova.com', 'System Administrator', TRUE);
```

### Step 2: Build & Deploy

```bash
cd C:\Users\ADMIN\AppData\Local\PD_WEBAPP
mvn clean package
# Copy target/pd_webapp.war to Tomcat webapps folder
# Restart Tomcat
```

### Step 3: Test Login

- URL: http://localhost:8080/pd_webapp/admin/login
- Username: `admin`
- Password: `admin123`

---

## What Changed

✅ **AdminUserDAO.java** - NEW class for database authentication
✅ **AdminLoginServlet.java** - Now uses database instead of hardcoded credentials
✅ **pom.xml** - Removed unnecessary dependencies

---

## Password Hash Details

- **Hash:** `ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=`
- **Password:** `admin123`
- **Algorithm:** SHA-256 with 16-byte random salt
- **Format:** Base64_Salt$Base64_Hash

---

## 📚 Documentation Files

1. **ADMIN_LOGIN_FIX_SUMMARY.md** - Complete detailed guide
2. **ADMIN_LOGIN_UPDATE.md** - Technical documentation
3. **ADMIN_LOGIN_INSERT.sql** - All SQL commands
4. **This file** - Quick setup checklist

---

## ✅ Verification Checklist

- [ ] MySQL database is running
- [ ] `cybernova_db` database exists
- [ ] `admin_users` table exists
- [ ] INSERT command executed successfully
- [ ] Project builds without errors (`mvn clean package`)
- [ ] WAR file deployed to Tomcat
- [ ] Tomcat restarted
- [ ] Login works with admin/admin123
- [ ] Admin dashboard loads successfully

---

## 🆘 If Something Goes Wrong

1. Check Tomcat logs: `TOMCAT_HOME/logs/catalina.out`
2. Verify MySQL is running: `mysql -u root -p`
3. Check admin user exists: `SELECT * FROM admin_users WHERE username='admin';`
4. Rebuild project: `mvn clean compile`
5. Check Java version is 17 or higher: `java -version`

---

## 🎯 Next Steps After Successful Login

1. Access the admin dashboard
2. Add more admin users using `AdminUserDAO.hashPassword()`
3. Implement additional admin features as needed
4. Never store plain text passwords!

---

**REFERENCE HASH FOR admin123:**
```
ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=
```

**COPY & PASTE SQL:**
```sql
INSERT INTO admin_users (username, password_hash, email, full_name, is_active) VALUES ('admin', 'ZUYg4W+rgrWc7ZfBLISIuA==$yRa5EdrqKUewPW6WqPjNHeMZojxRreedBj6YdMpoLsE=', 'admin@cybernova.com', 'System Administrator', TRUE);
```

---

**Status: ✅ READY TO DEPLOY**

