package me.bristermitten.minekraft.packet.login

import com.soywiz.korio.stream.AsyncInputStream
import me.bristermitten.minekraft.packet.InPacket
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.server.streams.readMCString

object LoginStartPacket : InPacket<LoginStartPacket.LoginData>(0x00){

    data class LoginData(val username: String) : PacketData

    override suspend fun read(stream: AsyncInputStream): LoginData {
        return LoginData(stream.readMCString(16))
    }
}
