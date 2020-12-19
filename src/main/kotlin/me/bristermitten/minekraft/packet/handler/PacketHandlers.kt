package me.bristermitten.minekraft.packet.handler

import me.bristermitten.minekraft.packet.Packet
import me.bristermitten.minekraft.packet.PacketData
import me.bristermitten.minekraft.packet.handshaking.Handshaking
import me.bristermitten.minekraft.packet.handshaking.HandshakingPacketHandler
import me.bristermitten.minekraft.packet.login.EncryptionResponseHandler
import me.bristermitten.minekraft.packet.login.EncryptionResponsePacket
import me.bristermitten.minekraft.packet.login.LoginStartHandler
import me.bristermitten.minekraft.packet.login.LoginStartPacket
import me.bristermitten.minekraft.packet.processor.EncryptionProcessor
import me.bristermitten.minekraft.packet.status.StatusPingHandler
import me.bristermitten.minekraft.packet.status.StatusPingPacket
import me.bristermitten.minekraft.packet.status.StatusRequestHandler
import me.bristermitten.minekraft.packet.status.StatusRequestPacket

val packetHandlers = mapOf(
    Handshaking to listOf(HandshakingPacketHandler),
    StatusRequestPacket to listOf(StatusRequestHandler),
    StatusPingPacket to listOf(StatusPingHandler),

    LoginStartPacket to listOf(LoginStartHandler),

    EncryptionResponsePacket to listOf(EncryptionResponseHandler)
)

val preprocessors = listOf(
    EncryptionProcessor
)

@Suppress("UNCHECKED_CAST")
val Packet<*>.handlers
    get() = packetHandlers[this] as List<IncomingPacketHandler<Packet<in PacketData>, in PacketData>>? //horrible safe cast to avoid Nothing hell
        ?: throw IllegalArgumentException("No handlers for $this")
