package me.bristermitten.minekraft.packet.login

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.writeBytes
import me.bristermitten.minekraft.encoding.writeVarInt
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writeMCString

object EncryptionRequestPacket : OutPacket<EncryptionRequestPacket.EncryptionData>(0x01) {

    override suspend fun write(stream: AsyncOutputStream, data: EncryptionData) {
        with(data) {
            stream.writeMCString(id, 20)
            stream.writeVarInt(publicKey.size)
            stream.writeBytes(publicKey)
            stream.writeVarInt(verifyToken.size)
            stream.writeBytes(verifyToken)
        }
    }

    data class EncryptionData(val id: String, val publicKey: ByteArray, val verifyToken: ByteArray) : PacketData {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is EncryptionData) return false

            if (id != other.id) return false
            if (!publicKey.contentEquals(other.publicKey)) return false
            if (!verifyToken.contentEquals(other.verifyToken)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + publicKey.contentHashCode()
            result = 31 * result + verifyToken.contentHashCode()
            return result
        }
    }
}
