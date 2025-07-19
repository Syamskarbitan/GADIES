# Setup Java Environment untuk Project Android
# Jalankan script ini jika java command tidak dikenali di terminal baru

Write-Host "Setting up Java 17 environment..." -ForegroundColor Green

# Set JAVA_HOME dari environment variable yang sudah disimpan
$env:JAVA_HOME = [Environment]::GetEnvironmentVariable("JAVA_HOME", "User")

# Refresh PATH dengan menggabungkan User dan Machine PATH
$env:PATH = [Environment]::GetEnvironmentVariable("PATH", "User") + ";" + [Environment]::GetEnvironmentVariable("PATH", "Machine")

# Verifikasi Java
Write-Host "Checking Java installation..." -ForegroundColor Yellow
try {
    java -version
    Write-Host "✅ Java berhasil dikonfigurasi!" -ForegroundColor Green
} catch {
    Write-Host "❌ Java masih belum bisa diakses" -ForegroundColor Red
}

# Verifikasi Gradle
Write-Host "`nChecking Gradle..." -ForegroundColor Yellow
try {
    .\gradlew --version
    Write-Host "✅ Gradle berhasil dikonfigurasi!" -ForegroundColor Green
} catch {
    Write-Host "❌ Gradle masih bermasalah" -ForegroundColor Red
}

Write-Host "`n🎉 Setup selesai! Sekarang Anda bisa build project Android." -ForegroundColor Cyan
