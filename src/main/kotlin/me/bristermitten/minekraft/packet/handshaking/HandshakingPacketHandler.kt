package me.bristermitten.minekraft.packet.handshaking

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.packet.State
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler

object HandshakingPacketHandler : IncomingPacketHandler<Handshaking, Handshaking.HandshakingData> {

    override suspend fun handle(client: MineKraftClient, data: Handshaking.HandshakingData) {
        val newState = State.fromCode(data.nextState)

        client.currentState = newState
        println("Updated ${client.address} to new state $newState")
    }
}
