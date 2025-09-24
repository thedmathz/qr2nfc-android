package com.example.nfc_helper.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nfc_helper.ui.screens.NFCWriteScreen
import com.example.nfc_helper.ui.screens.QRCodeScannerScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MyApp(
    onQrScanned: (String) -> Unit,
    setNfcScreenActive: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "scanner") {
        composable("scanner") {
            QRCodeScannerScreen(onQrScanned = { qrValue ->
                onQrScanned(qrValue)
                val encodedQr = URLEncoder.encode(qrValue, StandardCharsets.UTF_8.toString())
                navController.navigate("nfc/$encodedQr")
            })
            setNfcScreenActive(false)
        }

        composable(
            "nfc/{qrValue}",
            arguments = listOf(navArgument("qrValue") { type = NavType.StringType })
        ) { backStackEntry ->
            val qrValueEncoded = backStackEntry.arguments?.getString("qrValue") ?: ""
            val qrValue = URLDecoder.decode(qrValueEncoded, StandardCharsets.UTF_8.toString())

            setNfcScreenActive(true) // NFCWriteScreen is now active

            NFCWriteScreen(qrValue) {
                navController.popBackStack()
                setNfcScreenActive(false) // mark inactive when leaving
            }
        }
    }
}
