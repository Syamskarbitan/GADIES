@echo off
echo ========================================
echo GADIES Android App - Release Build
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
echo Running tests...
call gradlew.bat test
if %ERRORLEVEL% neq 0 (
    echo WARNING: Some tests failed, but continuing...
)

echo.
echo Building release APK...
call gradlew.bat assembleRelease
if %ERRORLEVEL% neq 0 (
    echo ERROR: Release build failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Build completed successfully!
echo ========================================
echo.
echo APK location: app\build\outputs\apk\release\app-release.apk
echo.

if exist "app\build\outputs\apk\release\app-release.apk" (
    echo Opening APK folder...
    explorer "app\build\outputs\apk\release"
) else (
    echo APK file not found in expected location.
    echo Please check: app\build\outputs\apk\release\
)

pause
