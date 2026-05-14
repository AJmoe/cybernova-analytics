# PD_WEBAPP

CyberNova Analytics Ltd prototype for the BSc Computer Systems Engineering cybersecurity assignment.

## What it does
- Public website for cybersecurity solutions, case studies, cyber blog posts, testimonials, workshops, and the security team contact form
- Password-protected admin login
- Admin dashboard with request filtering and analytics
- Embedded H2 database for security request storage

## Current restored pieces
- `pom.xml`
- `src/main/webapp/index.jsp`
- `src/main/webapp/WEB-INF/web.xml`
- `src/main/resources/META-INF/beans.xml`
- Public servlets and JSPs for CyberNova pages
- Admin login/dashboard pages
- `src/main/java/com/taskproject/pd_webapp/service/SecurityRequestRepository.java`
- `src/main/java/com/taskproject/pd_webapp/util/DBConnection.java`
- `src/main/webapp/assets/css/main.css`
- `src/main/webapp/assets/js/main.js`

## Default admin credentials
- Username: `admin`
- Password: `CyberNova@2026`

## Run
From the project root:

```powershell
mvn clean package
```

Then deploy the WAR to Tomcat 11.

## Notes
- The landing page is `/` and redirects to `/home`.
- The security request form posts to `/submit-security-request`.
- The admin dashboard reads from the H2-backed `SecurityRequestRepository`.
