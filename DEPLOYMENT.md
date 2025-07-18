# GADIES Android App - Deployment Guide

## 📱 Build & Deployment Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK API 34
- Gradle 8.4+

### Local Development Build

#### Debug Build
```bash
# Windows
.\build-debug.bat

# Linux/Mac
./gradlew assembleDebug
```

#### Release Build
```bash
# Windows
.\build-release.bat

# Linux/Mac
./gradlew assembleRelease
```

### Testing

#### Unit Tests
```bash
./gradlew test
```

#### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

#### Lint Check
```bash
./gradlew lint
```

## 🚀 Google Play Store Deployment

### 1. Prepare Release Build

1. **Update Version**
   - Edit `app/build.gradle.kts`
   - Increment `versionCode` and `versionName`

2. **Generate Signed APK**
   ```bash
   # Create keystore (first time only)
   keytool -genkey -v -keystore gadies-release-key.keystore -alias gadies -keyalg RSA -keysize 2048 -validity 10000
   
   # Build signed APK
   ./gradlew assembleRelease
   ```

3. **Sign APK**
   - Use Android Studio: Build → Generate Signed Bundle/APK
   - Or use command line with keystore

### 2. Play Console Setup

1. **Create App Listing**
   - App name: "GADIES - Suzuki Ertiga Diesel OBD2"
   - Short description: "Professional OBD2 diagnostic tool for Suzuki Ertiga Diesel vehicles"
   - Full description: See `PLAY_STORE_DESCRIPTION.md`

2. **Upload Assets**
   - App icon: `logo.png` (512x512)
   - Feature graphic: Create 1024x500 banner
   - Screenshots: Capture from emulator/device

3. **Content Rating**
   - Target audience: Everyone
   - No sensitive content

4. **Privacy Policy**
   - Required for apps accessing device data
   - Host on website or GitHub Pages

### 3. Release Process

1. **Internal Testing**
   - Upload APK to Internal Testing track
   - Test with team members

2. **Closed Testing**
   - Create closed testing track
   - Invite Gadies-Jatim community members
   - Collect feedback and fix issues

3. **Open Testing (Beta)**
   - Release to open testing
   - Monitor crash reports and reviews

4. **Production Release**
   - Promote from testing to production
   - Gradual rollout (10% → 50% → 100%)

## 📊 Release Checklist

### Pre-Release
- [ ] All tests passing
- [ ] Lint warnings resolved
- [ ] Version number updated
- [ ] Release notes prepared
- [ ] Screenshots updated
- [ ] Privacy policy updated

### Release
- [ ] APK signed with release key
- [ ] Upload to Play Console
- [ ] Release notes added
- [ ] Staged rollout configured
- [ ] Monitor crash reports

### Post-Release
- [ ] Monitor user reviews
- [ ] Track analytics
- [ ] Prepare hotfix if needed
- [ ] Plan next version features

## 🔧 Build Variants

### Debug
- Debuggable: true
- Minify: false
- Shrink resources: false
- ProGuard: disabled

### Release
- Debuggable: false
- Minify: true
- Shrink resources: true
- ProGuard: enabled

## 📈 Analytics & Monitoring

### Recommended Tools
- **Firebase Crashlytics** - Crash reporting
- **Firebase Analytics** - User behavior
- **Play Console** - App performance
- **Firebase Performance** - App performance monitoring

### Key Metrics to Track
- App crashes and ANRs
- User engagement
- Feature usage
- OBD2 connection success rate
- AI diagnostic usage

## 🔐 Security Considerations

### API Keys
- Store in `local.properties` (not in VCS)
- Use BuildConfig for runtime access
- Implement key rotation strategy

### ProGuard Rules
- Keep OBD2 communication classes
- Preserve Hilt injection
- Maintain Gson serialization

### Permissions
- Request only necessary permissions
- Implement runtime permission handling
- Provide clear permission rationale

## 📞 Support & Maintenance

### User Support
- GitHub Issues for bug reports
- Email: gadies.jatim@gmail.com
- Community forum integration

### Update Strategy
- Monthly feature updates
- Weekly bug fixes if needed
- Emergency hotfixes within 24h

### Backward Compatibility
- Support Android API 21+
- Graceful degradation for older devices
- Migration guides for major updates

## 🎯 Success Metrics

### Technical KPIs
- Crash rate < 1%
- ANR rate < 0.5%
- App startup time < 3s
- OBD2 connection success > 90%

### Business KPIs
- Play Store rating > 4.0
- Monthly active users growth
- User retention rate
- Community engagement

---

**Author:** Samsul Arifin (Gadies-Jatim)  
**Contact:** gadies.jatim@gmail.com  
**Donation:** BCA 0181539509
