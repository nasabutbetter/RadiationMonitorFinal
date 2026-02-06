package com.example.radiationmonitor

import com.example.radiationmonitor.generated.RadiationMonitor
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.crypto.Credentials
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.gas.GasProvider // Explicitly import GasProvider interface
import org.web3j.utils.Convert
import java.math.BigInteger
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Web3jClient(private val credentials: Credentials) {

    private val web3j: Web3j
    private lateinit var radiationMonitor: RadiationMonitor

    private val SEPOLIA_RPC_URL = "https://ethereum-sepolia-rpc.publicnode.com" // Use the same RPC as Hardhat
    private val CONTRACT_ADDRESS = "0x4B7AeA31835e65F40E7361d184c002E0d04A2e04" // Deployed contract address

    // Define reasonable gas price and gas limit for Sepolia transactions
    private val GAS_PRICE = BigInteger.valueOf(20_000_000_000L) // 20 Gwei
    private val GAS_LIMIT = BigInteger.valueOf(5_000_000L) // 5 million units

    init {
        web3j = Web3j.build(HttpService(SEPOLIA_RPC_URL))
        loadContract()
    }

    private fun loadContract() {
        val gasProvider: GasProvider = StaticGasProvider(GAS_PRICE, GAS_LIMIT) as GasProvider
        radiationMonitor = RadiationMonitor( // Use constructor directly
            CONTRACT_ADDRESS,
            web3j,
            credentials,
            gasProvider
        )
    }

    suspend fun getEthBalance(address: String): BigInteger {
        return withContext(Dispatchers.IO) {
            try {
                val ethGetBalance: EthGetBalance = web3j.ethGetBalance(address, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send()
                ethGetBalance.balance
            } catch (e: IOException) {
                e.printStackTrace()
                BigInteger.ZERO
            }
        }
    }

    suspend fun recordExposure(radiationLevel: BigInteger) {
        return withContext(Dispatchers.IO) {
            try {
                radiationMonitor.recordExposure(radiationLevel).send()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun getRadiationReadings(userAddress: String): List<RadiationMonitor.Reading> {
        return withContext(Dispatchers.IO) {
            try {
                radiationMonitor.getReadingsForUser(userAddress).send()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}
