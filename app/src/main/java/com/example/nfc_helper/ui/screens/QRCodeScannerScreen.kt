package com.example.nfc_helper.ui.screens

import android.Manifest
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CompoundBarcodeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.DisposableEffect
import android.widget.Toast

@Composable
fun QRCodeScannerScreen(onQrScanned: (String) -> Unit) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> permissionGranted = granted }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (permissionGranted) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            var scannerViewRef: CompoundBarcodeView? by remember { mutableStateOf(null) }

            AndroidView(
                factory = { ctx ->
                    val scannerView = CompoundBarcodeView(ctx).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setStatusText("Please scan your product QR Code to set the NFC Tag")
                        decodeContinuous { result ->
                            result.text?.let { qr ->
                                onQrScanned(qr)
                                pause() // stop scanning once detected
                            }
                        }
                    }
                    scannerViewRef = scannerView
                    scannerView
                },
                modifier = Modifier.fillMaxSize()
            )

            // Lifecycle handling: start/stop camera
            DisposableEffect(scannerViewRef) {
                scannerViewRef?.resume() // start camera preview
                onDispose {
                    scannerViewRef?.pause() // stop camera when Composable leaves
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Camera permission required")
        }
    }
}
