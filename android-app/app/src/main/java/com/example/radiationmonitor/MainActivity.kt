package com.example.radiationmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.radiationmonitor.generated.RadiationMonitor // Corrected import
import com.example.radiationmonitor.ui.theme.RadiationMonitorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.utils.Convert
import java.math.BigInteger
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadiationMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RadiationMonitorApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadiationMonitorApp() {
    val context = LocalContext.current
    val walletManager = remember { WalletManager(context) }
    
    var web3jClient: Web3jClient? by remember { mutableStateOf(null) }

    var walletAddress by remember { mutableStateOf("Loading...") }
    var ethBalance by remember { mutableStateOf("0.0 ETH") }
    var radiationLevelInput by remember { mutableStateOf("") }
    val radiationReadings = remember { mutableStateListOf<RadiationMonitor.Reading>() }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val actualRefreshData: suspend () -> Unit = {
        try {
            val credentials = walletManager.getWalletCredentials()
            if (credentials != null) {
                walletAddress = credentials.address

                val balanceWei = web3jClient!!.getEthBalance(credentials.address)
                ethBalance = Convert.fromWei(balanceWei.toBigDecimal(), Convert.Unit.ETHER).toPlainString() + " ETH"

                radiationReadings.clear()
                val fetchedReadings = web3jClient!!.getRadiationReadings(credentials.address)
                radiationReadings.addAll(fetchedReadings)
            } else {
                errorMessage = "Wallet not found. Please create a wallet."
            }
        } catch (e: Exception) {
            errorMessage = "Error refreshing data: ${e.message}"
            e.printStackTrace()
        } 
    }

    LaunchedEffect(Unit) {
        try {
            if (!walletManager.walletExists()) {
                walletManager.generateAndSaveWallet()
            }
            val credentials = walletManager.getWalletCredentials()
            if (credentials != null) {
                walletAddress = credentials.address
                web3jClient = Web3jClient(credentials)

                // Initial fetch of data
                actualRefreshData()
            } else {
                errorMessage = "Failed to load wallet."
            }
        } catch (e: Exception) {
            errorMessage = "Error initializing app: ${e.message}"
            e.printStackTrace()
        } finally {
            showLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Wallet Address: $walletAddress", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("ETH Balance: $ethBalance", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = radiationLevelInput,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    radiationLevelInput = newValue
                }
            },
            label = { Text("Radiation Level") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val level = radiationLevelInput.toBigIntegerOrNull()
                if (level != null && web3jClient != null) {
                    showLoading = true
                    errorMessage = null
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    coroutineScope.launch {
                        try {
                            web3jClient!!.recordExposure(level)
                            actualRefreshData()
                        } catch (e: Exception) {
                            errorMessage = "Error recording exposure: ${e.message}"
                            e.printStackTrace()
                        } finally {
                            showLoading = false
                        }
                    }
                } else {
                    errorMessage = "Please enter a valid radiation level."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !showLoading
        ) {
            Text("Record Exposure")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (showLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let { message ->
            Text(message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Radiation Readings:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(radiationReadings) { reading ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Level: ${reading.radiationLevel}", style = MaterialTheme.typography.bodyMedium)
                        Text("Timestamp: ${Instant.ofEpochSecond(reading.timestamp.toLong())}", style = MaterialTheme.typography.bodySmall)
                        Text("Recorder: ${reading.user}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadiationMonitorAppPreview() {
    RadiationMonitorTheme {
        RadiationMonitorApp()
    }
}