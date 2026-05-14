## 🔐 Password Reset - Quick Reference

### ⚡ The Fastest Way (2 Minutes)

**Step 1: Run the batch file**
```
Double-click: RESET_ADMIN_PASSWORD.bat
```

**Step 2: Follow prompts**
```
Enter admin username: [press Enter for 'admin']
Enter your NEW password: [type new password]
Confirm password: [type it again]
```

**Step 3: Copy the hash** provided

**Step 4: Update database** using the SQL command from Step 3

---

### 🖥️ Alternative: Command Line

```powershell
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"
java ResetAdminPassword
```

---

### 📊 Database Update Methods

#### Method A: Command Line (Fastest)
```powershell
mysql -h localhost -u root -pPASSWORD@123 cybernova_db
```
Then paste:
```sql
UPDATE admin_users SET password_hash = 'YOUR_HASH_HERE' WHERE username = 'admin';
```

#### Method B: phpMyAdmin (Easiest)
1. Go to: http://localhost/phpmyadmin
2. Select: cybernova_db → admin_users
3. Edit your row
4. Paste hash in password_hash field
5. Save

#### Method C: MySQL Workbench
1. Open MySQL Workbench
2. Connect to localhost (root/PASSWORD@123)
3. Select cybernova_db
4. Run the UPDATE command

---

### ✅ After Reset

```
URL:      http://localhost:8080/pd_webapp/admin/login
Username: admin
Password: [YOUR NEW PASSWORD]
```

---

### 🆘 Quick Troubleshooting

| Problem | Solution |
|---------|----------|
| "javac not found" | Java not installed - install from oracle.com |
| "MySQL error" | MySQL not running - start it in XAMPP/WAMP |
| "Access denied" | Use: `-pPASSWORD@123` (with P capital, @123) |
| Hash not working | Make sure you copied the entire hash |

---

### 📁 Files You Need

```
✅ ResetAdminPassword.java       (The tool)
✅ ResetAdminPassword.class      (Compiled)
✅ RESET_ADMIN_PASSWORD.bat      (Easy launcher)
✅ PASSWORD_RESET_GUIDE.md       (Full guide)
✅ PASSWORD_RESET_QUICK_REF.md   (This file)
```

---

### 🎯 Quick Decisions

**I want the easiest way:**
→ Double-click `RESET_ADMIN_PASSWORD.bat`

**I'm comfortable with command line:**
→ Run `java ResetAdminPassword`

**I need detailed help:**
→ Read `PASSWORD_RESET_GUIDE.md`

**I want to see what I'm doing:**
→ Use phpMyAdmin GUI

---

### 💾 Database Info

```
Database: cybernova_db
User:     root
Password: PASSWORD@123
Host:     localhost:3306
Table:    admin_users
Column:   password_hash
```

---

### 🔒 Security Notes

✅ Salt is regenerated each time (unique hash every time!)  
✅ SHA-256 algorithm (secure)  
✅ No plain text passwords in database  
✅ Hash can't be reversed to get password  

---

### ⏱️ Time Required

```
Tool Execution:    2 minutes
Database Update:   1 minute
Verification:      1 minute
─────────────────────────────
Total:            ~4 minutes
```

---

### 🎊 All Set!

You have everything you need. Just follow the steps above!

Good luck! 🚀

