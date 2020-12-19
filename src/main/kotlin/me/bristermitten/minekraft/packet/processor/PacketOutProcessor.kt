package me.bristermitten.minekraft.packet.processor

import me.bristermitten.minekraft.client.MineKraftClient

interface PacketOutProcessor {
    suspend fun processOut(client: MineKraftClient, byteArray: ByteArray) : ByteArray
}
