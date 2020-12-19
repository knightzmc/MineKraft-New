package me.bristermitten.minekraft.packet.status

import com.soywiz.korio.stream.AsyncInputStream
import me.bristermitten.minekraft.packet.EmptyPacketData
import me.bristermitten.minekraft.packet.InPacket
import me.bristermitten.minekraft.packet.PacketData

object StatusRequestPacket : InPacket<EmptyPacketData>(0x00) {

    override suspend fun read(stream: AsyncInputStream): EmptyPacketData {
        //empty packet
        return EmptyPacketData
    }
}
