package me.bristermitten.minekraft.packet.processor

import com.soywiz.korio.stream.AsyncInputStream
import me.bristermitten.minekraft.client.MineKraftClient

interface PacketInProcessor {
    suspend fun processIn(client: MineKraftClient): AsyncInputStream
}
