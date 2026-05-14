## 🔐 Admin Password Reset Guide

### ❌ You Forgot Your Password? No Problem!

This guide will help you reset your admin login password securely.

---

## 🚀 Quick Reset (5 Minutes)

### Step 1: Run the Password Reset Tool

```powershell
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"
javac ResetAdminPassword.java
java ResetAdminPassword
```

### Step 2: Follow the Prompts

```
╔════════════════════════════════════════════════════╗
║    ADMIN PASSWORD RESET UTILITY - CyberNova       ║
╚════════════════════════════════════════════════════╝

Enter admin username (default: admin): [PRESS ENTER or type your username]
Enter your NEW password: [TYPE YOUR NEW PASSWORD]
Confirm password: [TYPE IT AGAIN]
```

### Step 3: Copy the Hash

The tool will generate something like:

```
UPDATE admin_users SET password_hash = 'ABC123...XYZ456' WHERE username = 'admin';
```

### Step 4: Update Your Database

**Option A: Via Command Line (Easiest)**

```powershell
mysql -h localhost -u root -pPASSWORD@123 cybernova_db
```

Paste the command:

```sql
UPDATE admin_users SET password_hash = 'PASTE_THE_HASH_HERE' WHERE username = 'admin';
```

**Option B: Via phpMyAdmin**

1. Open `http://localhost/phpmyadmin`
2. Select database: `cybernova_db`
3. Click table: `admin_users`
4. Find your username row
5. Click "Edit"
6. Paste the hash in `password_hash` field
7. Click "Go"

### Step 5: Login

Go to: `http://localhost:8080/pd_webapp/admin/login`

```
Username: admin
Password: [YOUR NEW PASSWORD]
```

✅ You're in!

---

## 🔍 How It Works

### Why This Hash?

Your application stores passwords securely using:
- **Algorithm**: SHA-256
- **Salt**: 16 random bytes (regenerated each time)
- **Format**: `Base64(Salt)$Base64(Hash)`

Example:
```
Input:   "MyNewPassword123"
↓
Generate random salt (16 bytes)
↓
SHA-256(salt + password)
↓
Output:  "aBcDeF123...kLmNop$xYz456...vwxYz"
```

This ensures:
✅ No two hashes are the same (even for same password)
✅ Impossible to reverse engineer the password
✅ Industry-standard security

---

## 📋 What You Need

- ✅ This guide
- ✅ Java installed (to run ResetAdminPassword.java)
- ✅ MySQL running (to update database)
- ✅ MySQL credentials: `root` / `PASSWORD@123`

---

## 🆘 Troubleshooting

### Problem: "javac: command not found"

**Solution**: Java is not installed or not in PATH

```powershell
# Install Java or add to Path
java -version
```

### Problem: Can't connect to MySQL

**Solution**: MySQL is not running

```powershell
# Start MySQL
net start MySQL80
# Or check XAMPP/WAMP control panel
```

### Problem: "Access denied for user 'root'"

**Solution**: Wrong password. Default in your system is `PASSWORD@123`

```powershell
mysql -h localhost -u root -pPASSWORD@123 cybernova_db
```

### Problem: Hash Command Failed

**Solution**: Copy the exact hash from the tool output

```
❌ WRONG: UPDATE admin_users SET password_hash = 'ABC...'
✅ RIGHT: UPDATE admin_users SET password_hash = 'aBcDeF123...kLmNop$xYz456...vwxYz' WHERE username = 'admin';
```

---

## 🔐 Database Details

```
Host:     localhost
Port:     3306
Database: cybernova_db
User:     root
Password: PASSWORD@123
Table:    admin_users
Column:   password_hash
```

---

## ✅ Verify It Worked

After resetting, verify:

```sql
mysql> SELECT username, password_hash FROM admin_users WHERE username = 'admin';
```

You should see:
- `username`: admin
- `password_hash`: Your new hash (like: `aBcDe...XyZ$fGhI...WxYz`)

---

## 📲 Login

```
URL:      http://localhost:8080/pd_webapp/admin/login
Username: admin
Password: [YOUR NEW PASSWORD]
```

---

## 🎊 Success!

Once logged in, you'll see the admin dashboard.

If there's an error, check:
1. ✅ MySQL is running
2. ✅ Hash was copied correctly
3. ✅ SQL command executed successfully
4. ✅ Password matches what you set in the tool

---

## 💡 For Future Reference

### Store Your Password Somewhere Safe

Consider:
- ✅ Password manager (1Password, LastPass, Bitwarden)
- ✅ Encrypted file
- ✅ Secure note
- ⚠️ NOT in plain text emails

### Change It Periodically

For security, change your admin password every 90 days.

---

## 🆓 Free Tools Mentioned

- **Password Manager**: [Bitwarden](https://bitwarden.com) (free)
- **Text Editor**: [VS Code](https://code.visualstudio.com) (free)
- **Database Manager**: [phpMyAdmin](http://localhost/phpmyadmin) (included)

---

## 📞 Still Need Help?

1. Re-read the **Quick Reset** section above
2. Check **Troubleshooting** section
3. Verify all credentials in **Database Details**
4. Look at **Sample Commands** below

---

## 📋 Sample Commands Reference

### Compile the Tool
```powershell
cd "C:\Users\ADMIN\AppData\Local\PD_WEBAPP"
javac ResetAdminPassword.java
```

### Run the Tool
```powershell
java ResetAdminPassword
```

### Connect to MySQL
```powershell
mysql -h localhost -u root -pPASSWORD@123 cybernova_db
```

### Update Password in MySQL
```sql
UPDATE admin_users 
SET password_hash = 'YOUR_HASH_HERE' 
WHERE username = 'admin';
```

### Verify the Update
```sql
SELECT username, password_hash FROM admin_users WHERE username = 'admin';
```

### Login URL
```
http://localhost:8080/pd_webapp/admin/login
```

---

## ✨ You've Got This!

Your password reset tool is ready.  Simply run it and follow the prompts!

**Total Time: ~5 minutes** ⏱️

---

**Created**: May 14, 2026  
**Tools**: ResetAdminPassword.java (already compiled)  
**Status**: ✅ Ready to use  

