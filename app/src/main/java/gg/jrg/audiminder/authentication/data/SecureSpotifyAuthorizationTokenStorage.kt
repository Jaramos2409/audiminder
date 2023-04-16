package gg.jrg.audiminder.authentication.data

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

class SecureSpotifyAuthorizationTokenStorage(context: Context) {

    private val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "secure_spotify_authorization_token_storage",
            Context.MODE_PRIVATE
        )

    init {
        if (!keystore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }

    fun storeAuthToken(token: String) {
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, keystore.getKey(KEY_ALIAS, null))
        }
        val encryptedData = cipher.doFinal(token.toByteArray(Charset.defaultCharset()))
        val iv = cipher.iv

        sharedPreferences.edit()
            .putString("auth_token", Base64.encodeToString(encryptedData, Base64.DEFAULT))
            .putString("auth_token_iv", Base64.encodeToString(iv, Base64.DEFAULT))
            .apply()
    }

    fun getAuthToken(): String? {
        val encryptedData = sharedPreferences.getString("auth_token", null) ?: return null
        val iv = sharedPreferences.getString("auth_token_iv", null) ?: return null

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(
                Cipher.DECRYPT_MODE,
                keystore.getKey(KEY_ALIAS, null),
                GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT))
            )
        }
        val decryptedData = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
        return String(decryptedData, Charset.defaultCharset())
    }

    private fun generateKey() {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(false)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    companion object {
        private const val KEY_ALIAS = "auth_token_key"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
