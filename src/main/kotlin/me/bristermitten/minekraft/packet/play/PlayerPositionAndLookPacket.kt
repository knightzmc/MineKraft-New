package me.bristermitten.minekraft.packet.play

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.writeF32BE
import com.soywiz.korio.stream.writeF64BE
import me.bristermitten.minekraft.encoding.writeVarInt
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writeByte

object PlayerPositionAndLookPacket : OutPacket<PlayerPositionAndLookPacket.Position>(0x34) {

    data class Position(
        val x: Double,
        val y: Double,
        val z: Double,
        val yaw: Float,
        val pitch: Float,
        val flags: Byte,
        val teleportId: Int
    ) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: Position) {
        with(stream) {
            writeF64BE(data.x)
            writeF64BE(data.y)
            writeF64BE(data.z)
            writeF32BE(data.yaw)
            writeF32BE(data.pitch)
            writeByte(data.flags)
            writeVarInt(data.teleportId)
        }
    }
}
