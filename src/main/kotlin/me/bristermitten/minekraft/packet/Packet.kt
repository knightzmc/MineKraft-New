package me.bristermitten.minekraft.packet

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.AsyncOutputStream

sealed class Packet<T : PacketData>(val id: Int)

abstract class InPacket<T : PacketData>(id: Int) : Packet<T>(id) {
    abstract suspend fun read(stream: AsyncInputStream): T
}

abstract class OutPacket<T : PacketData>(id: Int) : Packet<T>(id) {
    abstract suspend fun write(stream: AsyncOutputStream, data: T)
}
