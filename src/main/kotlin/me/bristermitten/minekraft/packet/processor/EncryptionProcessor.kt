package me.bristermitten.minekraft.packet.processor

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readAvailable
import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.server.streams.ByteArrayAsyncInputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

object EncryptionProcessor : PacketInProcessor, PacketOutProcessor {
    private const val SHARED_SECRET_ALGORITHM = "AES/CFB8/NoPadding"

    private val cipher = Cipher.getInstance(SHARED_SECRET_ALGORITHM)

    override suspend fun processIn(client: MineKraftClient): AsyncInputStream {
        if (client.encryptionKey == null) {
            return client
        }

        val bytes = client.readAvailable()

        val key = client.encryptionKey!!

        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))

        val decrypted = cipher.doFinal(bytes)

        return ByteArrayAsyncInputStream(decrypted, client)
    }

    override suspend fun processOut(client: MineKraftClient, byteArray: ByteArray): ByteArray {
        if (client.encryptionKey == null) {
            return byteArray
        }

        val key = client.encryptionKey!!
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(key.encoded))

        return cipher.doFinal(byteArray)
    }


}
