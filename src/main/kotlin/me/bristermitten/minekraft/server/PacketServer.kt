package me.bristermitten.minekraft.server

import com.soywiz.korio.net.createTcpServer
import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.packet.handler.handlers

class PacketServer {

    suspend fun start(port: Int) {
        val server = createTcpServer(port = port)

        server.listen { _client ->
            val client = MineKraftClient(_client.getAddress(), _client)

            val address = client.address

            suspend fun readAndHandlePacket() {
                val (packet, data) = client.readPacket()

                packet.handlers.forEach {
                    it.handle(client, data)
                }
            }

            while (client.connected) {
                readAndHandlePacket()
                if (!client.connected) {
                    println("Disconnected ${client.address}")
                    break
                }
            }

            ClientState.clear(address)
        }
    }

    companion object {
        const val STATE_KEY = "mc_state"
    }
}
