package com.example.nfc_helper

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.nfc_helper.ui.MyApp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    var currentQrValue: String = ""
    var nfcWriteScreenActive: Boolean = false
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Compose UI
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MyApp(
                        onQrScanned = { qrValue ->
                            currentQrValue = qrValue
                        },
                        setNfcScreenActive = { active ->
                            nfcWriteScreenActive = active
                        }
                    )
                }
            }
        }

        // NFC adapter and pending intent
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (!nfcWriteScreenActive) return // only write when NFCWriteScreen is visible

        intent?.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action ||
                NfcAdapter.ACTION_NDEF_DISCOVERED == it.action) {

                val tag: Tag? = it.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                tag?.let { t ->
                    writeNfcTag(t, currentQrValue)
                }
            }
        }
    }

    private fun writeNfcTag(tag: Tag, data: String) {
        try {
            val ndef = Ndef.get(tag)
            ndef.connect()

            val record = if (data.startsWith("http://") || data.startsWith("https://")) {
                // Write as URI so system can open browser
                NdefRecord.createUri(data)
            } else {
                // Write as plain text
                NdefRecord.createTextRecord("en", data)
            }

            val message = NdefMessage(arrayOf(record))
            ndef.writeNdefMessage(message)
            ndef.close()

            Toast.makeText(this, "NFC tag set: $data", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to write NFC tag", Toast.LENGTH_SHORT).show()
        }
    }

}
