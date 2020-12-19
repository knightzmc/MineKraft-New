package me.bristermitten.minekraft.packet.login

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.data.NBT
import me.bristermitten.minekraft.encryption.ServerEncryption
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler
import me.bristermitten.minekraft.packet.play.JoinGamePacket
import me.bristermitten.minekraft.server.ClientState
import kotlin.random.Random

object LoginStartHandler : IncomingPacketHandler<LoginStartPacket, LoginStartPacket.LoginData> {
    override suspend fun handle(client: MineKraftClient, data: LoginStartPacket.LoginData) {
        println("Username ${data.username} logging in...")

        val id = ""
        val publicKey = ServerEncryption.public.encoded

        val verifyToken = ByteArray(4)
        ServerEncryption.random.nextBytes(verifyToken)

        ClientState.setState(client.address, VERIFY_TOKEN_KEY, verifyToken.copyOf())

        val encryptionData = EncryptionRequestPacket.EncryptionData(id, publicKey, verifyToken)
        client.writePacket(EncryptionRequestPacket, encryptionData)
    }

    const val VERIFY_TOKEN_KEY = "mk_verify_token"
}
