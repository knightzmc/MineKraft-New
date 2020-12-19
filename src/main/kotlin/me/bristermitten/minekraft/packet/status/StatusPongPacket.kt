package me.bristermitten.minekraft.packet.status

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.write64BE
import me.bristermitten.minekraft.packet.OutPacket

object StatusPongPacket : OutPacket<StatusPingPacket.Ping>(0x01) {
    override suspend fun write(stream: AsyncOutputStream, data: StatusPingPacket.Ping) {
        stream.write64BE(data.number)
    }
}
