# MySQL Setup Script for CyberNova Analytics
# Run this in PowerShell to create the database

$mysqlPath = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
$scriptPath = "C:\Users\ADMIN\AppData\Local\PD_WEBAPP\scripts\mysql-setup.sql"

Write-Host "CyberNova Analytics - MySQL Database Setup" -ForegroundColor Cyan
Write-Host "===========================================" -ForegroundColor Cyan
Write-Host ""

# Verify files exist
if (-not (Test-Path $mysqlPath)) {
    Write-Host "ERROR: MySQL not found at $mysqlPath" -ForegroundColor Red
    Write-Host "Please install MySQL Server 8.0" -ForegroundColor Yellow
    pause
    exit 1
}

if (-not (Test-Path $scriptPath)) {
    Write-Host "ERROR: Setup script not found at $scriptPath" -ForegroundColor Red
    pause
    exit 1
}

Write-Host "Found MySQL: $mysqlPath" -ForegroundColor Green
Write-Host "Found script: $scriptPath" -ForegroundColor Green
Write-Host ""
Write-Host "Starting database setup..." -ForegroundColor Yellow
Write-Host ""

# Run MySQL setup
$setupContent = Get-Content $scriptPath -Raw
$setupContent | & $mysqlPath -u root -p

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "============================================" -ForegroundColor Green
    Write-Host "SUCCESS! Database created!" -ForegroundColor Green
    Write-Host "============================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Database: cybernova_analytics" -ForegroundColor Cyan
    Write-Host "Tables: 6 (security_requests, admin_users, etc.)" -ForegroundColor Cyan
    Write-Host "Sample data: 5 requests loaded" -ForegroundColor Cyan
    Write-Host "Admin: admin / admin123" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "1. Restart Tomcat" -ForegroundColor Yellow
    Write-Host "2. Access: http://localhost:8080/pd_webapp/admin/login" -ForegroundColor Yellow
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "ERROR: Setup failed with error code: $LASTEXITCODE" -ForegroundColor Red
    Write-Host "Check your MySQL password and try again." -ForegroundColor Yellow
}

pause

