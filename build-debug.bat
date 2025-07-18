@echo off
echo ========================================
echo GADIES Android App - Debug Build
echo ========================================
echo.

echo Cleaning previous builds...
call gradlew.bat clean
if %ERRORLEVEL% neq 0 (
    echo ERROR: Clean failed!
    pause
    exit /b 1
)

echo.
echo Building debug APK...
call gradlew.bat assembleDebug
if %ERRORLEVEL% neq 0 (
    echo ERROR: Debug build failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Debug build completed successfully!
echo ========================================
echo.
echo APK location: app\build\outputs\apk\debug\app-debug.apk
echo.

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo Opening APK folder...
    explorer "app\build\outputs\apk\debug"
) else (
    echo APK file not found in expected location.
    echo Please check: app\build\outputs\apk\debug\
)

pause
