@echo off
REM Create CyberNova Analytics Database with New Schema v2.0

set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe
set SCRIPT_PATH=C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql
set MYSQL_PASSWORD=PASSWORD@123

echo.
echo =====================================================
echo   CyberNova Analytics - Database Setup v2.0
echo =====================================================
echo.
echo Creating NEW database: cybernova_analytics
echo Your old ai_solutions_db will NOT be affected
echo.
echo Schema includes:
echo  - Security Requests Management
echo  - Admin User Management
echo  - Request Notes & Communications
echo  - Status History & Audit Trail
echo  - Case Studies & Blog Articles
echo  - Testimonials & Gallery
echo.

"%MYSQL_PATH%" -u root -p"%MYSQL_PASSWORD%" < "%SCRIPT_PATH%"

if %errorlevel% equ 0 (
    echo.
    echo =====================================================
    echo SUCCESS! New database created!
    echo =====================================================
    echo.
    echo Database: cybernova_analytics (NEW)
    echo Tables: 8 tables with complete schema
    echo Admin Login: admin / PASSWORD@123
    echo Sample Data: 5 security requests loaded
    echo.
    echo Your old ai_solutions_db is STILL INTACT
    echo.
) else (
    echo.
    echo ERROR: Setup failed with code %errorlevel%
    echo Please verify MySQL password and path
    echo.
)

pause

