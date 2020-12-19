package me.bristermitten.minekraft.packet.handler

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.packet.Packet
import me.bristermitten.minekraft.packet.PacketData

interface IncomingPacketHandler<T : Packet<R>, R : PacketData> {
    suspend fun handle(client: MineKraftClient, data: R)
}
