package me.bristermitten.minekraft.packet.status

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readS64BE
import me.bristermitten.minekraft.packet.InPacket
import me.bristermitten.minekraft.packet.PacketData

object StatusPingPacket : InPacket<StatusPingPacket.Ping>(0x01) {

    data class Ping(val number: Long) : PacketData

    override suspend fun read(stream: AsyncInputStream): Ping {
        return Ping(stream.readS64BE())
    }
}
