@echo off
REM Check what MySQL databases exist
REM Usage: Run this without any input required

echo Checking MySQL databases...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -h localhost -e "SHOW DATABASES;" > "C:\Users\ADMIN\AppData\Local\PD_WEBAPP\database_list.txt" 2>&1
echo.
echo Databases found (saved to database_list.txt):
type "C:\Users\ADMIN\AppData\Local\PD_WEBAPP\database_list.txt"
echo.
pause

