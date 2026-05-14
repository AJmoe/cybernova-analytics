@echo off
REM MySQL Setup Script for CyberNova Analytics
REM This script will create the database and tables needed for the application

echo.
echo ============================================
echo  CyberNova Analytics - MySQL Setup
echo ============================================
echo.

REM Set the path to MySQL executable
set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe
set SCRIPT_PATH=C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql

REM Check if MySQL is installed
if not exist "%MYSQL_PATH%" (
    echo ERROR: MySQL not found at %MYSQL_PATH%
    echo Please install MySQL or verify the installation path.
    pause
    exit /b 1
)

REM Check if setup script exists
if not exist "%SCRIPT_PATH%" (
    echo ERROR: Setup script not found at %SCRIPT_PATH%
    pause
    exit /b 1
)

echo Found MySQL at: %MYSQL_PATH%
echo Found setup script at: %SCRIPT_PATH%
echo.
echo Executing MySQL setup script...
echo Please enter your MySQL root password when prompted (press Enter if no password):
echo.

REM Execute the MySQL script
"%MYSQL_PATH%" -u root -p < "%SCRIPT_PATH%"

if %errorlevel% equ 0 (
    echo.
    echo ============================================
    echo SUCCESS! Database created successfully!
    echo ============================================
    echo.
    echo The following have been created:
    echo  - Database: cybernova_analytics
    echo  - Tables: 6 (security_requests, admin_users, etc.)
    echo  - Sample data: 5 security requests loaded
    echo  - Admin user: admin / admin123
    echo.
    echo You can now restart Tomcat and access the application at:
    echo   http://localhost:8080/pd_webapp/admin/login
    echo.
) else (
    echo.
    echo ERROR: Setup failed. Error code: %errorlevel%
    echo Please check your MySQL installation and try again.
    echo.
)

pause

