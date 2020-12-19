package me.bristermitten.minekraft.packet.play

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.writeBytes
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writeIdentifier

object PluginMessageOutPacket : OutPacket<PluginMessageOutPacket.Message>(0x17) {

    data class Message(val channel: Identifier, val data: ByteArray) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: Message) {
        stream.writeIdentifier(data.channel)
        stream.writeBytes(data.data)
    }
}
