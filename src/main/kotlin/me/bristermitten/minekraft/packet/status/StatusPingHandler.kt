package me.bristermitten.minekraft.packet.status

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler


object StatusPingHandler : IncomingPacketHandler<StatusPingPacket, StatusPingPacket.Ping> {
    override suspend fun handle(client: MineKraftClient, data: StatusPingPacket.Ping) {
        client.writePacket(StatusPongPacket, data)

        client.close()
    }

}
