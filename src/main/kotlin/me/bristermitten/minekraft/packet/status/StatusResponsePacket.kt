package me.bristermitten.minekraft.packet.status

import com.soywiz.korio.stream.AsyncOutputStream
import me.bristermitten.minekraft.packet.OutPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.writeMCString

object StatusResponsePacket : OutPacket<StatusResponsePacket.Response>(0x00){

    data class Response(val json: String) : PacketData

    override suspend fun write(stream: AsyncOutputStream, data: Response) {
        stream.writeMCString(data.json)
    }


}

