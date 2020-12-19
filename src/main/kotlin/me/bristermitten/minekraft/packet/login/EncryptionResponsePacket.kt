package me.bristermitten.minekraft.packet.login

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readBytesExact
import me.bristermitten.minekraft.packet.InPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.readVarInt

object EncryptionResponsePacket : InPacket<EncryptionResponsePacket.EncryptionResponseData>(0x01) {

    data class EncryptionResponseData(val sharedSecret: ByteArray, val verifyToken: ByteArray) : PacketData

    override suspend fun read(stream: AsyncInputStream): EncryptionResponseData {
        val ssLen = stream.readVarInt()
        val ss = stream.readBytesExact(128).sliceArray(0 until ssLen) //128 bytes are sent because of padding

        val verifyLen = stream.readVarInt()
        val verify = stream.readBytesExact(128).sliceArray(0 until verifyLen)

        return EncryptionResponseData(ss, verify)
    }
}
