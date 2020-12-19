package me.bristermitten.minekraft.packet.status

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.packet.EmptyPacketData
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler
import org.intellij.lang.annotations.Language

object StatusRequestHandler : IncomingPacketHandler<StatusRequestPacket, EmptyPacketData> {
    override suspend fun handle(client: MineKraftClient, data: EmptyPacketData) {
        @Language("JSON")
        val json = """
          {"version":{"name":"1.16.4","protocol":754},"players":{"max":100,"online":2,"sample":[{"name":"§c§lHello","id":"928d9867-e31a-4a03-bc4b-cd62d8182daf"},{"name":"§9§lWorld!","id":"1691fdfb-e9b3-4a42-acdd-50a94e2ff07f"}]},"description":{"text":"MineKraft is a Minecraft Server written in Kotlin!","bold":true,"color":"green"}}
        """.trimIndent()

        client.writePacket(StatusResponsePacket, StatusResponsePacket.Response(json))
    }
}
