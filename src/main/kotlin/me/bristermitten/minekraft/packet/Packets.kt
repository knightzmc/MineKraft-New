package me.bristermitten.minekraft.packet

import me.bristermitten.minekraft.packet.handshaking.Handshaking
import me.bristermitten.minekraft.packet.login.EncryptionResponsePacket
import me.bristermitten.minekraft.packet.login.LoginStartPacket
import me.bristermitten.minekraft.packet.status.StatusPingPacket
import me.bristermitten.minekraft.packet.status.StatusRequestPacket

val incomingPackets = mapOf(
    State.Handshaking to mapOf<Int, InPacket<*>>(
        Handshaking.id to Handshaking
    ),

    State.Status to mapOf(
        StatusRequestPacket.id to StatusRequestPacket,
        StatusPingPacket.id to StatusPingPacket
    ),

    State.Login to mapOf(
        LoginStartPacket.id to LoginStartPacket,
        EncryptionResponsePacket.id to EncryptionResponsePacket
    )
)
