# Request Detail Workflow - Setup Checklist

## ✅ Authentication Protection
- [x] `/admin/request` endpoint is protected by `AdminAuthFilter`
- [x] Requires active admin session to access
- [x] Unauthenticated users redirected to login
- [x] Session timeout: 30 minutes

## ✅ Database Setup
- [x] MySQL setup script created: `scripts/mysql-setup.sql`
- [x] All 6 tables defined with proper indexes
- [x] Sample security request data included
- [x] Admin user created (username: admin, password: admin123)

## ✅ Java Code
- [x] DBConnection.java updated for MySQL connectivity
- [x] Error messages include troubleshooting guidance
- [x] pom.xml updated with MySQL JDBC dependency

## 🔄 Next Actions Required

### 1. MySQL Database Setup (RUN ONCE)
Execute the SQL setup script:

```bash
# Option A: MySQL CLI
mysql -u root -p < scripts/mysql-setup.sql

# Option B: MySQL Workbench
# - Open mysql-setup.sql
# - Execute the entire script
```

**What this does:**
- Creates `cybernova_analytics` database
- Creates 6 tables with proper structure
- Inserts sample security requests
- Inserts default admin user

---

### 2. Update Database Credentials (IF NEEDED)
If your MySQL differs from default settings:

**File**: `src/main/java/com/taskproject/pd_webapp/util/DBConnection.java`

Update these lines if needed:
```java
private static final String DB_HOST = "localhost";           // Change if MySQL not on localhost
private static final int DB_PORT = 3306;                     // Change if using custom port
private static final String DB_USER = "root";                // Change if not using root
private static final String DB_PASSWORD = "";                // Add password if needed
```

Example with password:
```java
private static final String DB_PASSWORD = "your_mysql_password";
```

---

### 3. Build the Project
```bash
mvn clean install
```

This will:
- Download MySQL JDBC driver (8.0.33)
- Compile all Java files
- Package as WAR file

---

### 4. Deploy to Servlet Container
Deploy the WAR file from `target/pd_webapp.war` to your:
- Apache Tomcat
- Jetty
- GlassFish
- Or other Jakarta EE container

---

### 5. Test the Workflow
1. Open browser: `http://localhost:8080/pd_webapp/admin/login`
2. Login with: `admin` / `admin123`
3. Go to dashboard: `http://localhost:8080/pd_webapp/admin/dashboard`
4. Click "View details" on any request
5. Update the status and click "Save status"
6. Verify it redirects with success message
7. Check MySQL that status persisted:
   ```sql
   SELECT id, name, status FROM security_requests WHERE id = 1;
   ```

---

## Project Files Updated

| File | Change | Status |
|------|--------|--------|
| `src/main/java/.../util/DBConnection.java` | Switched from H2 to MySQL | ✅ Updated |
| `pom.xml` | Added MySQL JDBC driver | ✅ Updated |
| `scripts/mysql-setup.sql` | Created MySQL schema | ✅ Created |
| `docs/MYSQL_SETUP.md` | Setup documentation | ✅ Created |
| `src/main/webapp/.../request-detail.jsp` | Request detail page | ✅ Created |
| `src/main/java/.../AdminRequestDetailServlet.java` | Handle GET/POST | ✅ Created |

---

## Authentication Details

**Filter**: `AdminAuthFilter`
- **Pattern**: `/admin/*`
- **Protects**: All admin endpoints including `/admin/request`
- **Allows**: `/admin/login` (no auth required)
- **Session**: Uses `adminAuthenticated` flag in session
- **Timeout**: 30 minutes (from web.xml)

**Current Credentials** (from seed data):
- Username: `admin`
- Password: `admin123`

⚠️ **IMPORTANT**: Change these credentials in production before going live!

---

## Database Connection Flow

1. Application starts
2. `SecurityRequestRepository.getInstance()` is called
3. Calls `DBConnection.ensureInitialized()`
4. Attempts to query `security_requests` table
5. If successful: initialized = true
6. If fails: Shows detailed error with troubleshooting steps

---

## Sample SQL Queries

### View all requests
```sql
SELECT * FROM security_requests ORDER BY created_at DESC;
```

### View requests by status
```sql
SELECT * FROM security_requests WHERE status = 'In Review';
```

### Update request status
```sql
UPDATE security_requests SET status = 'Closed' WHERE id = 1;
```

### View analytics
```sql
SELECT 
  COUNT(*) as total_requests,
  COUNT(DISTINCT issue_type) as unique_issue_types,
  COUNT(DISTINCT country) as unique_countries
FROM security_requests;
```

---

## Troubleshooting Checklist

- [ ] MySQL server is running (`sudo systemctl start mysql` on Linux)
- [ ] Database `cybernova_analytics` exists
- [ ] Tables created successfully
- [ ] User `root` can access the database
- [ ] Application configuration matches your MySQL setup
- [ ] Maven cleaned and rebuilt (`mvn clean install`)
- [ ] WAR deployed to servlet container
- [ ] Application started without errors (check logs)
- [ ] Can reach login page: `http://localhost:8080/pd_webapp/admin/login`
- [ ] Can login with admin/admin123
- [ ] Can see dashboard with requests
- [ ] Can view request details
- [ ] Can update status and see it in database

---

## Success Criteria

✅ Request detail workflow is complete when:
1. User can authenticate to `/admin/request`
2. Request details display correctly
3. Status dropdown shows all 4 options
4. Status change persists in MySQL
5. Success message displays after update
6. Back button returns to dashboard

---

**Need help?** Check `docs/MYSQL_SETUP.md` for detailed troubleshooting.

