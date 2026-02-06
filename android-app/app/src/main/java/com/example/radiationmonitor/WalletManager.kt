package com.example.radiationmonitor

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException

class WalletManager(private val context: Context) {

    private val KEY_ALIAS = "my_ethereum_key"
    private val PREF_FILE_NAME = "wallet_prefs"
    private val PRIVATE_KEY_TAG = "private_key"

    private lateinit var masterKey: MasterKey
    private lateinit var encryptedSharedPreferences: SharedPreferences

    init {
        try {
            masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
                        .build()
                )
                .build()

            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREF_FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error, e.g., prompt user to restart app or clear app data
        }
    }

    fun getWalletCredentials(): Credentials? {
        val privateKey = encryptedSharedPreferences.getString(PRIVATE_KEY_TAG, null)
        return if (privateKey != null) {
            Credentials.create(privateKey)
        } else {
            null
        }
    }

    fun generateAndSaveWallet(): Credentials {
        val ecKeyPair = Keys.createEcKeyPair()
        val privateKey = ecKeyPair.privateKey.toString(16) // Convert BigInteger to hex string
        encryptedSharedPreferences.edit().putString(PRIVATE_KEY_TAG, privateKey).apply()
        return Credentials.create(ecKeyPair)
    }

    fun walletExists(): Boolean {
        return encryptedSharedPreferences.contains(PRIVATE_KEY_TAG)
    }
}
