# Request Detail Workflow - Architecture

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                         USER'S WEB BROWSER                          │
└────────────────────────────────┬────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    1. AdminAuthFilter                               │
│  @WebFilter("/admin/*")                                             │
│  ├─ Intercepts: /admin/request?id=123                              │
│  ├─ Checks: Is user authenticated?                                 │
│  ├─ If NO → Redirect to: /admin/login                              │
│  └─ If YES → Allow request to proceed                              │
└────────────────────────────────┬────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│            2. AdminRequestDetailServlet                             │
│  @WebServlet("/admin/request")                                      │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │ GET /admin/request?id=1                                     │  │
│  ├─ Parse ID from query parameter                              │  │
│  ├─ Call: repository.findById(1)  ──────────────────┐          │  │
│  ├─ Set: request.setAttribute("securityRequest", ...) │        │  │
│  ├─ Set: request.setAttribute("statusOptions", [...]) │        │  │
│  └─ Forward to: request-detail.jsp ◄──────────────────┘        │  │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │ POST /admin/request (from form submission)                  │  │
│  ├─ Parse: id=1, status="Closed"                               │  │
│  ├─ Call: repository.updateStatus(1, "Closed") ────────┐       │  │
│  └─ Redirect to: /admin/request?id=1&updated=1 ◄──────┘       │  │
│                                                                     │
└─────────────────────────────────┬────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│            3. SecurityRequestRepository                             │
│  Singleton managing database access                                 │
│                                                                     │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │ findById(1): SecurityRequest                               │   │
│  │ ├─ SQL: SELECT * FROM security_requests WHERE id = ?       │   │
│  │ ├─ Execute query with ID parameter                         │   │
│  │ └─ Map ResultSet to SecurityRequest object                 │   │
│  └────────────────────────────────────────────────────────────┘   │
│                                                                     │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │ updateStatus(1, "Closed"): boolean                         │   │
│  │ ├─ SQL: UPDATE security_requests SET status = ? WHERE id=? │   │
│  │ ├─ Execute update with status and ID parameters            │   │
│  │ └─ Return: true if successful                              │   │
│  └────────────────────────────────────────────────────────────┘   │
│                                                                     │
└─────────────────────────────────┬────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│                      4. DBConnection                                │
│  Manages MySQL connectivity with connection pooling                │
│                                                                     │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │ ensureInitialized()                                        │   │
│  │ ├─ On first app startup:                                   │   │
│  │ │  ├─ Load MySQL JDBC Driver (com.mysql.cj.jdbc.Driver)   │   │
│  │ │  ├─ Test connection to security_requests table           │   │
│  │ │  ├─ If fails: Throw detailed error with next steps       │   │
│  │ │  └─ If success: Set initialized=true                     │   │
│  │ └─ Subsequent calls: Skip (already initialized)             │   │
│  │                                                             │   │
│  │ getConnection(): Connection                                │   │
│  │ ├─ Ensure initialized first                                │   │
│  │ └─ Return: New MySQL connection via DriverManager          │   │
│  └────────────────────────────────────────────────────────────┘   │
│                                                                     │
└─────────────────────────────────┬────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    5. MySQL Database                                │
│  cybernova_analytics database                                       │
│                                                                     │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │ Table: security_requests                                   │   │
│  │ Columns: id, name, email, phone, organization, country,   │   │
│  │          job_title, issue_type, description, status,       │   │
│  │          created_at, updated_at                            │   │
│  │                                                             │   │
│  │ Indexes: status, issue_type, country, created_at           │   │
│  │                                                             │   │
│  │ Sample Data:                                               │   │
│  │ ┌─────┬────────┬──────────────┬────────────────────────┐  │   │
│  │ │ ID  │ Name   │ Status       │ Issue Type             │  │   │
│  │ ├─────┼────────┼──────────────┼────────────────────────┤  │   │
│  │ │ 1   │ Amina  │ New          │ Phishing Response      │  │   │
│  │ │ 2   │ Tebogo │ New          │ Vulnerability Assess.  │  │   │
│  │ │ 3   │ Sipho  │ In Review    │ Incident Response      │  │   │
│  │ │ 4   │ Naledi │ New          │ Security Training      │  │   │
│  │ │ 5   │ Kabelo │ New          │ Cloud Hardening        │  │   │
│  │ └─────┴────────┴──────────────┴────────────────────────┘  │   │
│  └────────────────────────────────────────────────────────────┘   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Request Flow Sequence Diagram

### GET Request (View Request Details)

```
User                  Filter              Servlet          Repository        DB
  │                    │                    │                  │              │
  ├─ Click "View       │                    │                  │              │
  │  Details" Link     │                    │                  │              │
  │ /admin/request     │                    │                  │              │
  │    ?id=1           │                    │                  │              │
  ├──────────────────>│                    │                  │              │
  │                   │ Check session       │                  │              │
  │                   │ authenticated?      │                  │              │
  │                   │ (YES)               │                  │              │
  │                   ├──────────────────>│                  │              │
  │                   │                    │ parseId("1")      │              │
  │                   │                    ├─> 1              │              │
  │                   │                    │                  │              │
  │                   │                    │ findById(1)       │              │
  │                   │                    ├─────────────────>│              │
  │                   │                    │                  │ SELECT * FROM │
  │                   │                    │                  │ security_req. │
  │                   │                    │                  ├─────────────>│
  │                   │                    │                  │              │
  │                   │                    │                  │ ResultSet    │
  │                   │                    │<─────────────────┤<─────────────┤
  │                   │                    │ mapRequest()     │              │
  │                   │                    │ returns:         │              │
  │                   │                    │ SecurityRequest  │              │
  │                   │                    ├─ id: 1           │              │
  │                   │                    ├─ name: "Amina"   │              │
  │                   │                    └─ status: "New"   │              │
  │                   │                    │                  │              │
  │                   │                    │ setAttribute()    │              │
  │                   │                    │ forward to JSP    │              │
  │                   │<──────────────────┤                  │              │
  │<──────────────────────────────────────┤                  │              │
  │                  200 + request-detail.jsp rendered       │              │
  │
```

### POST Request (Update Status)

```
User                  Filter              Servlet          Repository        DB
  │                    │                    │                  │              │
  ├─ Change status     │                    │                  │              │
  │  "New" → "Closed"  │                    │                  │              │
  │ Submit form        │                    │                  │              │
  │ POST /admin/       │                    │                  │              │
  │    request         │                    │                  │              │
  ├──────────────────>│                    │                  │              │
  │                   │ Check session       │                  │              │
  │                   │ authenticated?      │                  │              │
  │                   │ (YES)               │                  │              │
  │                   ├──────────────────>│                  │              │
  │                   │                    │ parseId("1")      │              │
  │                   │                    │ getParameter      │              │
  │                   │   ("status")        │                  │              │
  │                   │                    │ "Closed"          │              │
  │                   │                    │                  │              │
  │                   │                    │ updateStatus      │              │
  │                   │                    │ (1, "Closed")     │              │
  │                   │                    ├─────────────────>│              │
  │                   │                    │                  │ UPDATE set    │
  │                   │                    │                  │ status=?      │
  │                   │                    │                  │ WHERE id=?    │
  │                   │                    │                  ├─────────────>│
  │                   │                    │                  │              │
  │                   │                    │                  │ 1 row updated│
  │                   │                    │                  │<─────────────┤
  │                   │                    │                  │              │
  │                   │                    │ return: true      │              │
  │                   │                    ├─────────────────>│              │
  │                   │                    │ sendRedirect()    │              │
  │                   │                    │ /admin/request    │              │
  │                   │                    │ ?id=1&updated=1   │              │
  │                   │<──────────────────┤                  │              │
  │<──────────────────────────────────────┤                  │              │
  │                  302 Redirect                             │              │
  │                                                           │              │
  ├─ Follow redirect  │                    │                  │              │
  │ GET /admin/       │                    │                  │              │
  │    request?       │                    │                  │              │
  │    id=1&updated=1 │                    │                  │              │
  ├──────────────────>│                    │                  │              │
  │                   │ (Same as above GET request)           │              │
  │                   │ But parameter updated=1 shows         │              │
  │                   │ success message in JSP                │              │
  │                   │                    │                  │              │
  │<──────────────────────────────────────┤                  │              │
  │ 200 + JSP with "Status updated        │                  │              │
  │     successfully" message shown         │                  │              │
```

---

## Error Handling Flow

```
Application Start
  │
  ▼
DBConnection.ensureInitialized() called
  │
  ├─ Try to load MySQL JDBC Driver
  │  │
  │  └─ If FAILS: ClassNotFoundException
  │     │
  │     └─ Throw: "MySQL JDBC Driver not available"
  │
  ├─ Try to connect to MySQL
  │  │
  │  └─ If FAILS: SQLException
  │     │
  │     └─ Throw: Detailed error message
  │        ├─ "MySQL is running on localhost:3306?"
  │        ├─ "Database 'cybernova_analytics' exists?"
  │        ├─ "User 'root' can access the database?"
  │        ├─ "Run the setup script: scripts/mysql-setup.sql?"
  │        └─ "Error: {actual error message}"
  │
  └─ If SUCCESS: initialized = true
     └─ Application starts normally

```

---

## Authentication Protection Matrix

```
┌──────────────────────────────┬────────────────────┬─────────────────┐
│ URL Pattern                  │ Requires Auth?     │ Redirects To    │
├──────────────────────────────┼────────────────────┼─────────────────┤
│ /                            │ NO                 │ N/A             │
│ /index.jsp                   │ NO                 │ N/A             │
│ /home                        │ NO                 │ N/A             │
│ /articles                    │ NO                 │ N/A             │
│ /admin/login                 │ NO                 │ N/A             │
│ /admin/dashboard             │ YES                │ /admin/login    │
│ /admin/request?id=1          │ YES ✅             │ /admin/login    │
│ /admin/logout                │ YES                │ /admin/login    │
│ /admin/anything-else         │ YES                │ /admin/login    │
└──────────────────────────────┴────────────────────┴─────────────────┘
```

---

## Component Interaction Summary

| Component | Interacts With | Type | Purpose |
|-----------|---|---|---|
| `AdminAuthFilter` | `HttpSession` | Filter | Validates admin authentication |
| `AdminRequestDetailServlet` | `SecurityRequestRepository` | Servlet | Handles GET/POST for request details |
| `SecurityRequestRepository` | `DBConnection` | Service | Database CRUD operations |
| `DBConnection` | MySQL Driver | Utility | Manages MySQL connection |
| `SecurityRequest` | All | Model | Data transfer object |
| `request-detail.jsp` | `SecurityRequest` | View | Renders HTML for display |

---

## Database Transaction Example

### Update Status Scenario

```
Admin updates request #1 from "New" to "In Review"

┌─ AdminRequestDetailServlet.doPost()
│  ├─ Receives: id="1", status="In Review"
│  ├─ Validates parameters
│  │
│  └─ Calls: repository.updateStatus(1, "In Review")
│     │
│     └─ SecurityRequestRepository.updateStatus()
│        ├─ Creates SQL: "UPDATE security_requests SET status=? WHERE id=?"
│        │
│        └─ Calls: DBConnection.getConnection()
│           │
│           └─ Gets MySQL Connection
│              │
│              └─ Executes: PreparedStatement.executeUpdate()
│                 │
│                 └─ MySQL Database
│                    │
│                    ├─ Finds row with id=1
│                    ├─ Updates status column to "In Review"
│                    └─ Returns: 1 (one row affected)
│
│  ├─ Receives: true (success)
│  │
│  └─ Calls: response.sendRedirect() with &updated=1 parameter
│     │
│     └─ Browser follows redirect
│        │
│        └─ GET /admin/request?id=1&updated=1
│           │
│           └─ Shows success message
```

---

## Performance Considerations

```
Optimization Strategy:
├─ Database Indexes
│  ├─ status: Faster filtering on dashboard
│  ├─ issue_type: Faster filtering on dashboard
│  ├─ country: Faster filtering on dashboard
│  └─ created_at: Faster sorting by date
│
├─ Connection Management
│  ├─ Reuses JDBC connections
│  └─ Tests connection on startup (fail-fast)
│
├─ Single Repository Instance
│  ├─ SecurityRequestRepository.getInstance()
│  ├─ Singleton pattern
│  └─ One instance per application lifecycle
│
└─ Lazy Initialization
   ├─ Validations only on first use
   ├─ Fails early with detailed error
   └─ Subsequent requests use cached initialization flag
```

---

**This architecture provides:**
- ✅ Security (Authentication filter)
- ✅ Maintainability (Clear separation of concerns)
- ✅ Performance (Indexes, connection reuse)
- ✅ Reliability (Error handling, validation)
- ✅ Scalability (Prepared statements, connection management)

