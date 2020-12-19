package me.bristermitten.minekraft.packet.play

import com.soywiz.korio.stream.AsyncOutputStream
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writePosition

object SpawnPositionPacket : OutPacket<SpawnPositionPacket.Position>(0x42) {

    data class Position(val x: Int, val y: Int, val z: Int) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: Position) {
        stream.writePosition(data.x, data.y, data.z)
    }
}
