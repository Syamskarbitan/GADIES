# GADIES - Garasi Diesel Suzuki

GADIES adalah aplikasi Android native yang dikembangkan khusus untuk kendaraan Suzuki Ertiga Diesel. Aplikasi ini menyediakan monitoring OBD2 real-time, analisis AI, dan diagnostik komprehensif untuk membantu pemilik kendaraan memantau kesehatan mesin mereka.

## 🚗 Fitur Utama

### 📊 Dashboard Real-time
- Monitoring suhu coolant mesin
- Monitoring tegangan aki/battery
- Status koneksi ELM327
- Peringatan dan alert aktif
- Aksi cepat untuk fungsi utama

### 📈 Live Data OBD2
- Tampilan 74 PID (Parameter ID) lengkap
- Kategorisasi hierarkis (Main Category → Sub Category → PID)
- Data Standard OBD-II, Suzuki Custom, dan D13A ECU
- UI expandable accordion untuk navigasi mudah
- Badge khusus untuk PID Suzuki-specific
- Toggle alert individual untuk setiap PID

### 🤖 AI Analysis & Chat
- Analisis kesehatan kendaraan menggunakan AI
- Chat dengan asisten mekanik AI (Deepseek R1)
- Rekomendasi maintenance dan troubleshooting
- Deteksi masalah potensial berdasarkan data PID

### 🔗 Konektivitas
- Support ELM327 Bluetooth dan WiFi
- Auto-scan dan koneksi perangkat
- Monitoring status koneksi real-time
- Mode offline dengan simulator data

### ⚙️ Pengaturan Lengkap
- Multi-bahasa (Indonesia/English)
- Theme system (Light/Dark/Auto)
- Kontrol notifikasi dan alert
- Pengaturan threshold PID
- Manajemen koneksi perangkat

## 🏗️ Arsitektur Teknis

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose dengan Material3
- **Architecture**: MVVM dengan StateFlow
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **JSON Parsing**: Gson
- **Charts**: MPAndroidChart
- **Permissions**: Accompanist Permissions
- **Storage**: DataStore Preferences

### Struktur Project
```
app/
├── src/main/java/com/gadies/suzuki/
│   ├── data/
│   │   ├── model/          # Data classes dan enums
│   │   ├── repository/     # Repository pattern
│   │   └── simulator/      # PID data simulator
│   ├── service/
│   │   ├── AiService.kt    # OpenRouter AI integration
│   │   ├── ObdService.kt   # OBD2 communication
│   │   └── NotificationService.kt
│   ├── ui/
│   │   ├── screen/         # Compose UI screens
│   │   ├── theme/          # Material3 theming
│   │   └── viewmodel/      # ViewModels
│   ├── MainActivity.kt
│   └── GadiesApplication.kt
├── src/main/assets/
│   ├── config.json         # App configuration
│   └── thresholds.json     # PID thresholds data
└── src/main/res/           # Android resources
```

## 📋 74 PID yang Didukung

### Kategorisasi PID:
1. **ENGINE SYSTEM** (25 PIDs)
   - Engine Performance: RPM, Load, MAF, MAP
   - Temperature Sensors: Coolant, Intake Air
   - Throttle & Load: TPS, Load Value
   - Fuel System: Fuel Trim, Fuel Pressure
   - Ignition System: Timing Advance, Spark

2. **VEHICLE SYSTEM** (15 PIDs)
   - Speed & Distance: Vehicle Speed, Distance
   - Transmission: Gear Position, Torque
   - Steering & Suspension: Steering Angle

3. **BATTERY/ELECTRICAL** (12 PIDs)
   - Voltage & Current: Battery Voltage, Current
   - Status & Health: SOC, Temperature
   - ISG System: Motor Speed, Status

4. **BRAKE SYSTEM** (8 PIDs)
   - Brake Pressure & Status
   - ABS & Brake Assist

5. **HVAC SYSTEM** (7 PIDs)
   - AC Compressor & Evaporator
   - Climate Control

6. **ENVIRONMENT SYSTEM** (4 PIDs)
   - Ambient Temperature & Pressure

7. **DIAGNOSTIC SYSTEM** (3 PIDs)
   - DTC Count & Status

### Sumber Data:
- **Standard OBD-II**: 22 PIDs (Mode 01)
- **Suzuki Custom**: 26 PIDs (Mode 21)
- **D13A ECU Specific**: 26 PIDs (Mode 22)

## 🎨 UI/UX Design

### Material3 Design System
- **Primary Color**: Yellow (#FFD700) - Identitas GADIES
- **Typography**: Material3 typography scale
- **Components**: Cards, Buttons, FAB, Navigation
- **Dark/Light Theme**: Automatic system detection

### Mobile-First Approach
- Responsive layout untuk berbagai ukuran layar
- Touch-friendly interface
- Swipe gestures dan animations
- Bottom navigation untuk akses mudah

## 🔧 Setup Development

### Prerequisites
- Android Studio Arctic Fox atau lebih baru
- JDK 8 atau lebih baru
- Android SDK API 21+ (Android 5.0)
- ELM327 device untuk testing (opsional)

### Build Instructions
1. Clone repository
2. Buka project di Android Studio
3. Sync Gradle dependencies
4. Build dan run di device/emulator

### Testing
- **Unit Tests**: JUnit untuk logic testing
- **UI Tests**: Compose testing untuk UI
- **Integration Tests**: Hilt testing untuk DI
- **Simulator Mode**: Testing tanpa ELM327 device

## 📱 Kompatibilitas

### Android Version
- **Minimum**: Android 5.0 (API 21)
- **Target**: Android 14 (API 34)
- **Recommended**: Android 8.0+ untuk fitur lengkap

### Hardware Requirements
- RAM: 2GB minimum, 4GB recommended
- Storage: 100MB untuk app + data
- Bluetooth: 2.0+ untuk ELM327 Bluetooth
- WiFi: 802.11b/g/n untuk ELM327 WiFi

### ELM327 Compatibility
- **Bluetooth**: ELM327 v1.5 atau lebih baru
- **WiFi**: ELM327 WiFi dengan IP 192.168.0.10
- **Protocols**: ISO 9141-2, KWP2000, CAN

## 🚀 Roadmap

### Version 1.1 (Planned)
- [ ] Real ELM327 device integration
- [ ] PID decoding formulas implementation
- [ ] Advanced data logging dan export
- [ ] Custom dashboard widgets

### Version 1.2 (Future)
- [ ] Multi-vehicle support
- [ ] Cloud sync dan backup
- [ ] Advanced AI diagnostics
- [ ] Maintenance scheduler

### Version 2.0 (Long-term)
- [ ] Tablet UI optimization
- [ ] Professional mechanic tools
- [ ] Fleet management features
- [ ] API untuk third-party integration

## 👨‍💻 Developer

**Samsul Arifin**
- Anggota Gadies-Jatim (Garasi Diesel Suzuki Jawa Timur)
- Email: [Contact via Gadies-Jatim]
- Donasi: BCA 0181539509

## 📄 License

Aplikasi ini dikembangkan untuk komunitas Suzuki Ertiga Diesel Indonesia.
Silakan gunakan dan modifikasi sesuai kebutuhan dengan tetap mencantumkan credit.

## 🙏 Acknowledgments

- Komunitas Gadies-Jatim untuk support dan testing
- OpenRouter untuk AI API integration
- Android Jetpack Compose team
- ELM327 protocol documentation contributors

---

**GADIES** - *Monitoring Suzuki Ertiga Diesel dengan Teknologi Modern* 🚗⚡
