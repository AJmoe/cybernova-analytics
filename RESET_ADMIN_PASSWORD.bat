@echo off
REM ╔═══════════════════════════════════════════════════════════╗
REM ║   ADMIN PASSWORD RESET TOOL - CyberNova Analytics        ║
REM ║   Easy one-click password reset                           ║
REM ╚═══════════════════════════════════════════════════════════╝

setlocal enabledelayedexpansion
cd /d "%~dp0"

echo.
echo ╔═══════════════════════════════════════════════════════════╗
echo ║   ADMIN PASSWORD RESET TOOL - CyberNova Analytics        ║
echo ║   Easy one-click password reset                           ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ ERROR: Java is not installed!
    echo.
    echo Please install Java and add it to your system PATH:
    echo https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo ✅ Java found!
echo.

REM Check if ResetAdminPassword.java exists
if not exist "ResetAdminPassword.java" (
    echo ❌ ERROR: ResetAdminPassword.java not found!
    echo.
    echo Please ensure you're running this from: C:\Users\ADMIN\AppData\Local\PD_WEBAPP
    echo.
    pause
    exit /b 1
)

echo ✅ Password reset tool found!
echo.

REM Compile if .class doesn't exist or is outdated
if not exist "ResetAdminPassword.class" (
    echo ⏳ Compiling password reset tool...
    javac ResetAdminPassword.java
    if errorlevel 1 (
        echo ❌ Compilation failed!
        pause
        exit /b 1
    )
    echo ✅ Compilation successful!
    echo.
)

REM Run the tool
echo Running password reset utility...
echo.
java ResetAdminPassword

echo.
echo ╔═══════════════════════════════════════════════════════════╗
echo ║   Password reset instructions provided above!             ║
echo ║   Follow the steps to update your database.               ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.
pause

