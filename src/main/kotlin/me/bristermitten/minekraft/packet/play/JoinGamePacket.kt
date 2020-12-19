package me.bristermitten.minekraft.packet.play

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.write32BE
import com.soywiz.korio.stream.write64BE
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.data.NBT
import me.bristermitten.minekraft.encoding.writeVarInt
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writeBoolean
import me.bristermitten.minekraft.server.streams.writeByte
import me.bristermitten.minekraft.server.streams.writeIdentifier
import me.bristermitten.minekraft.server.streams.writeNBT

object JoinGamePacket : OutPacket<JoinGamePacket.JoinGameData>(0x24) {

    data class JoinGameData(
        val eid: Int,
        val hardcore: Boolean,
        val gameMode: Byte,
        val previousGameMode: Byte,
        val worlds: List<Identifier>,
        val dimensionCodec: NBT,
        val dimension: NBT,
        val worldName: Identifier,
        val hashedSeed: Long,
        val maxPlayers: Int,
        val viewDistance: Int,
        val reducedDebugInfo: Boolean,
        val respawnScreen: Boolean,
        val debug: Boolean,
        val flat: Boolean
    ) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: JoinGameData) {
        with(stream) {
            write32BE(data.eid)
            writeBoolean(data.hardcore)
            writeByte(data.gameMode)
            writeByte(data.previousGameMode)
            writeVarInt(data.worlds.size)
            data.worlds.forEach {
                writeIdentifier(it)
            }

            writeNBT(data.dimensionCodec)
            writeNBT(data.dimension)

            writeIdentifier(data.worldName)
            write64BE(data.hashedSeed)
            writeVarInt(data.maxPlayers)
            writeVarInt(data.viewDistance)

            writeBoolean(data.reducedDebugInfo)
            writeBoolean(data.respawnScreen)
            writeBoolean(data.debug)
            writeBoolean(data.flat)
        }
    }
}
