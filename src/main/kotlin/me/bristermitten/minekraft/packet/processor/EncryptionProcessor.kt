package me.bristermitten.minekraft.packet.processor

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readAvailable
import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.server.streams.ByteArrayAsyncInputStream

object EncryptionProcessor : PacketInProcessor, PacketOutProcessor {


    override suspend fun processIn(client: MineKraftClient): AsyncInputStream {
        if (client.decryptionCipher == null) {
            return client
        }

        val bytes = client.readAvailable()

        val cipher = client.decryptionCipher!!
        val decrypted = cipher.doFinal(bytes)

        return ByteArrayAsyncInputStream(decrypted, client)
    }

    override suspend fun processOut(client: MineKraftClient, byteArray: ByteArray): ByteArray {
        if (client.encryptionCipher == null) {
            return byteArray
        }

        val cipher = client.encryptionCipher!!
        return cipher.doFinal(byteArray)
    }


}
