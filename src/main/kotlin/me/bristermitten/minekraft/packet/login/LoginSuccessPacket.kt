package me.bristermitten.minekraft.packet.login

import com.soywiz.korio.stream.AsyncOutputStream
import me.bristermitten.minekraft.packet.EmptyPacketData
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.server.streams.writeMCString
import me.bristermitten.minekraft.server.streams.writeUUID
import java.util.*

object LoginSuccessPacket : OutPacket<EmptyPacketData>(0x02) {

    override suspend fun write(stream: AsyncOutputStream, data: EmptyPacketData) {
        stream.writeUUID(UUID.fromString("876ce46d-dc56-4a17-9644-0be67fe7c7f6"))
        stream.writeMCString("BristerMitten")
    }
}
