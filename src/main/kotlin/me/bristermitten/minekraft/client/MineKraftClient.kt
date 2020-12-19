package me.bristermitten.minekraft.client

import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.writeBytes
import me.bristermitten.minekraft.encoding.getVarIntSize
import me.bristermitten.minekraft.encoding.writeVarInt
import me.bristermitten.minekraft.packet.*
import me.bristermitten.minekraft.packet.processor.EncryptionProcessor
import me.bristermitten.minekraft.server.streams.BufferedAsyncOutputStream
import me.bristermitten.minekraft.server.streams.readVarInt
import org.intellij.lang.annotations.Language
import java.net.SocketAddress
import javax.crypto.Cipher
import javax.crypto.SecretKey

class MineKraftClient(
    val address: SocketAddress,
    private val internalClient: AsyncClient
) : AsyncInputStream, AsyncOutputStream {

    internal var encryptionCipher: Cipher? = null
    internal var decryptionCipher: Cipher? = null
    internal var currentState = State.Handshaking

    val connected get() = internalClient.connected

    override suspend fun close() {
        internalClient.close()
    }

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        internalClient.write(buffer, offset, len)
    }

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        return internalClient.read(buffer, offset, len)
    }

    suspend fun <T : OutPacket<R>, R> writePacket(packet: T, data: R) {
        val buffer = BufferedAsyncOutputStream()
        buffer.writeVarInt(packet.id) //write id to initial buffer
        packet.write(buffer, data)  //write packet data to initial buffer


        //Then we make a new buffer for the final data, prepending the packet data's size
        val bytes = buffer.bytes

        val otherBuffer = BufferedAsyncOutputStream(getVarIntSize(bytes.size) + bytes.size)
        otherBuffer.writeVarInt(bytes.size)
        otherBuffer.writeBytes(bytes)

        val bytesToWrite = otherBuffer.bytes

        val encrypted = EncryptionProcessor.processOut(this, bytesToWrite) //Then perform encryption
        writeBytes(encrypted)

        println("Wrote ${encrypted.size} bytes for packet $packet with data $data")
    }

    suspend fun readPacket(): Pair<Packet<*>, PacketData> {
        val input = EncryptionProcessor.processIn(this)
        val len = input.readVarInt()
        if (len <= 0) {
            throw IllegalArgumentException("Packet length should never be less than 1")
        }

        val id = input.readVarInt()

        val packets = incomingPackets[currentState]!!
        val packet = packets[id] ?: run {
            throw IllegalArgumentException("Unknown packet with state $currentState and ID $id (length = $len)")
        }

        val data = packet.read(input)

        println(
            """
            ====
            Len = $len
            ID = $id
            State = $currentState
            Packet = $packet
            Data = $data
            ====
        """.trimIndent()
        )

        return packet to data
    }

}
