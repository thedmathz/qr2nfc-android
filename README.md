# 📱 QR Code → NFC Tag — Android

A lightweight Android app that scans a QR code and writes its value to an NFC tag. Perfect for quick physical-digital links like product pages, contactless menus, or smart posters.

✨ Highlights
- Read QR codes (supports URLs and plain text)
- Write the scanned value to an NFC tag (NDEF URL/text)
- Read NFC tags and optionally open URL values in a browser
- Simple, clean UI (see screenshots below)
- Small, self-contained Kotlin project
  
---

## 🖼️ Screenshots

### How to write NFC tag
<table>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/db25f30b-9016-4260-b2e9-06c07b950229" alt="img1" width="180"/><br/>Read QR code</td>
    <td align="center"><img src="https://github.com/user-attachments/assets/8eae7461-9810-4023-9ab0-0af5504f6718" alt="img1" width="180"/><br/>Scan to NFC tag to write</td>
    <td align="center"><img src="https://github.com/user-attachments/assets/0b30dbf6-6f23-4a45-8c77-da7424395215" alt="img1" width="180"/><br/>Success notification</td>
  </tr>
</table>

### How to read NFC tag
<table>
  <tr>
  <td align="center"><img src="https://github.com/user-attachments/assets/e6d69acc-eada-4618-be20-7dcb0312eae4" alt="img1" width="180"/><br/>Read NFC Tag</td>
  <td align="center"><img src="https://github.com/user-attachments/assets/564198a1-f66a-4a11-9782-4b39142de459" alt="img1" width="180"/><br/>Browse Value (if URL)</td>
  <td align="center"><img src="https://github.com/user-attachments/assets/48e8afa6-4cc8-4e1d-8c94-12949688fcec" alt="img1" width="180"/><br/>NFC tag</td>
  </tr>
</table>

--- 

## 💡 How it works (high level)
1. The app uses the camera + a QR scanning library to scan QR codes.
2. After a successful scan the app navigates to a new screen.
3. User taps an NFC tag to the phone → the app writes the value to the tag.
4. A toast notification confirms success.
5. For reading, the app listens for NFC discovery intents and parses any NDEF records it finds — if the payload is a URL the app can open it in the browser.

---

## 🔧 Requirements
- Android device with NFC hardware
- Android 8.0+ recommended (API 28+)
- Camera permission for QR scanning
- NFC permission and feature declared in AndroidManifest.xml

--- 

## 🛠️ How to Build

1. Clone the repository
  ```bash
  git clone https://github.com/yourname/qr-to-nfc-android.git
  cd qr-to-nfc-android
  ```
2. Open in Android Studio
- Launch Android Studio
- Select Open an existing project
- Choose the project folder you just cloned
3. Set up dependencies
- Sync the gradle
4. Enable NFC on your test device
- Go to device settings → turn on NFC
5. Run the project
- Connect an NFC-capable Android device via USB
- Press ▶️ Run in Android Studio
6. Test the flow
- Scan a QR code → see the value on screen
- Tap an NFC tag to write → confirm toast
- To check if it works, close the app, tap the tag again, and the value will be read back and opened in the browser (if it’s a URL).

---

## 🔌 Manifest: required permissions & features
Add these lines to your AndroidManifest.xml:
```cmd
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.NFC"/>

<uses-feature android:name="android.hardware.nfc" android:required="true"/>
<uses-feature android:name="android.hardware.camera.any" android:required="true"/>

<!-- application tag area -->
```

