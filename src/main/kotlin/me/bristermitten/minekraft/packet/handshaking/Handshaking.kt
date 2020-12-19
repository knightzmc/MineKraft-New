package me.bristermitten.minekraft.packet.handshaking

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readU16BE
import me.bristermitten.minekraft.packet.InPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.readMCString
import me.bristermitten.minekraft.server.streams.readVarInt

object Handshaking : InPacket<Handshaking.HandshakingData>(0x00) {
    override suspend fun read(stream: AsyncInputStream): HandshakingData {
        val version = stream.readVarInt()
        val address = stream.readMCString()
        val port = stream.readU16BE()
        val nextState = stream.readVarInt()

        return HandshakingData(version, address, port.toShort(), nextState)
    }

    data class HandshakingData(
        val protocolVersion: Int,
        val address: String,
        val port: Short,
        val nextState: Int
    ) : PacketData
}


