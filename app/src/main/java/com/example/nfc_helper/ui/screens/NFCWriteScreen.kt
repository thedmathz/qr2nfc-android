package com.example.nfc_helper.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun NFCWriteScreen(qrValue: String, onBack: () -> Unit) {
    val context = LocalContext.current
    var isWriting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Big NFC icon
        Icon(
            imageVector = Icons.Default.Nfc,
            contentDescription = "NFC Icon",
            tint = Color.Gray,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Scan instruction
        Text(
            text = "Scan your NFC tag",
            fontSize = 28.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Button to go back / scan another product
        Button(onClick = onBack) {
            Text(text = "Scan Other Product", fontSize = 18.sp)
        }

        // Simulate NFC writing for demo
        LaunchedEffect(isWriting) {
            if (!isWriting) {
                isWriting = true
                delay(2000) // simulate writing to NFC
                Toast.makeText(context, "NFC tag set with value: $qrValue", Toast.LENGTH_SHORT).show()
                isWriting = false
            }
        }
    }
}
