package me.bristermitten.minekraft.packet

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.writeBytes

object RawPacket  : OutPacket<RawPacket.RawPacketData>(-1){

    data class RawPacketData(val bytes: ByteArray) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: RawPacketData) {
        stream.writeBytes(data.bytes)
    }
}
