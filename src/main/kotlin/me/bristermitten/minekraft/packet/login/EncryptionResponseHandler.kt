package me.bristermitten.minekraft.packet.login

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.data.NBT
import me.bristermitten.minekraft.encryption.ServerEncryption
import me.bristermitten.minekraft.packet.EmptyPacketData
import me.bristermitten.minekraft.packet.State
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler
import me.bristermitten.minekraft.packet.play.JoinGamePacket
import me.bristermitten.minekraft.packet.play.PlayerPositionAndLookPacket
import me.bristermitten.minekraft.packet.play.SpawnPositionPacket
import me.bristermitten.minekraft.server.ClientState
import javax.crypto.spec.SecretKeySpec

object EncryptionResponseHandler :
    IncomingPacketHandler<EncryptionResponsePacket, EncryptionResponsePacket.EncryptionResponseData> {

    override suspend fun handle(client: MineKraftClient, data: EncryptionResponsePacket.EncryptionResponseData) {
        //decrypt and compare the verify token
        val decrypted = ServerEncryption.rsaDecrypt(data.verifyToken)

        val verifyToken = ClientState.getState(client.address, LoginStartHandler.VERIFY_TOKEN_KEY) as ByteArray

        require(decrypted.contentEquals(verifyToken)) {
            "Decrypted verify token was not the same!"
        }

        val secret = ServerEncryption.rsaDecrypt(data.sharedSecret)
        ClientState.setState(client.address, SHARED_SECRET_KEY, secret.copyOf())

        client.encryptionKey = SecretKeySpec(secret, "AES")

        client.writePacket(LoginSuccessPacket, EmptyPacketData)

        client.currentState = State.Play

        val overworldNBT = NBT(
            "", mapOf(
                "name" to "minecraft:overworld",
                "id" to 0,
                "element" to NBT(
                    "", mapOf(
                        "piglin_safe" to 0.toByte(),
                        "natural" to 1.toByte(),
                        "ambient_light" to 0.0f,
                        "infiniburn" to "minecraft:infiniburn_overworld",
                        "respawn_anchor_works" to 0.toByte(),
                        "has_skylight" to 1.toByte(),
                        "bed_works" to 1.toByte(),
                        "has_raids" to 1.toByte(),
                        "name" to "minecraft:overworld",
                        "logical_height" to 256,
                        "coordinate_scale" to 1.0,
                        "ultrawarm" to 0.toByte(),
                        "has_ceiling" to 0.toByte()
                    )
                )
            )
        )

        val dimensions = NBT(
            "", mapOf(
                "type" to "minecraft:dimension_type",
                "value" to listOf(overworldNBT)
            )
        )

        val biomes = NBT(
            "", mapOf(
                "type" to "minecraft:worldgen/biome",
                "value" to listOf(
                    NBT(
                        "", mapOf(
                            "name" to "minecraft:plains",
                            "id" to 0,
                            "element" to NBT(
                                "",
                                mapOf(
                                    "precipitation" to "rain",
                                    "depth" to 0.125f,
                                    "temperature" to 0.8f,
                                    "scale" to 0.05f,
                                    "downfall" to 0.4000000059604645,
                                    "category" to "none",
                                    "effects" to NBT(
                                        "", mapOf(
                                            "sky_color" to 7907327,
                                            "water_fog_color" to 329011,
                                            "fog_color" to 12638463,
                                            "water_color" to 4159204,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        val nbt = NBT(
            "",
            mapOf(
                "minecraft:dimension_type" to dimensions,
                "minecraft:worldgen/biome" to biomes
            ),
        )

        val joinGameData = JoinGamePacket.JoinGameData(
            eid = 0,
            hardcore = false,
            gameMode = 0,
            previousGameMode = -1,
            worlds = listOf(Identifier("world")),
            dimensionCodec = nbt,
            dimension = overworldNBT,
            worldName = Identifier("overworld"),
            hashedSeed = 0,
            maxPlayers = 0, //unused
            viewDistance = 8,
            reducedDebugInfo = false,
            respawnScreen = true,
            debug = false,
            flat = false
        )

        client.writePacket(JoinGamePacket, joinGameData)

        client.writePacket(SpawnPositionPacket, SpawnPositionPacket.Position(0, 0, 0))
        client.writePacket(
            PlayerPositionAndLookPacket,
            PlayerPositionAndLookPacket.Position(0.0, 0.0, 0.0, 0f, 0f, 0, 3)
        )

//        client.writePacket(
//            PluginMessageOutPacket, PluginMessageOutPacket.Message(
//                Identifier("brand"),
//                BufferedAsyncOutputStream().apply {
//                    writeMCString("MineKraft")
//                }.bytes
//            )
//        )
    }

    const val SHARED_SECRET_KEY = "mk_shared_secret"
}
