package me.bristermitten.minekraft.encryption

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher


object ServerEncryption {
    val random = SecureRandom()
    private val keyPair = generateKeyPair()
    val public: PublicKey = keyPair.public

    private fun generateKeyPair(): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(1024)
        return generator.genKeyPair()
    }

    fun rsaEncrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.public)
        return cipher.doFinal(bytes)
    }

    fun rsaDecrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, keyPair.private)
        return cipher.doFinal(bytes)
    }
}
