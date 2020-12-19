package me.bristermitten.minekraft.packet.login

import me.bristermitten.minekraft.client.MineKraftClient
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.encryption.ServerEncryption
import me.bristermitten.minekraft.packet.EmptyPacketData
import me.bristermitten.minekraft.packet.State
import me.bristermitten.minekraft.packet.handler.IncomingPacketHandler
import me.bristermitten.minekraft.packet.play.JoinGamePacket
import me.bristermitten.minekraft.packet.play.PlayerPositionAndLookPacket
import me.bristermitten.minekraft.packet.play.SpawnPositionPacket
import me.bristermitten.minekraft.server.ClientState
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTList
import org.jglrxavpok.hephaistos.nbt.NBTTypes

object LoginStartHandler : IncomingPacketHandler<LoginStartPacket, LoginStartPacket.LoginData> {
    override suspend fun handle(client: MineKraftClient, data: LoginStartPacket.LoginData) {
        println("Username ${data.username} logging in...")

//        val id = ""
//        val publicKey = ServerEncryption.public.encoded
//
//        val verifyToken = ByteArray(4)
//        ServerEncryption.random.nextBytes(verifyToken)
//
//        ClientState.setState(client.address, VERIFY_TOKEN_KEY, verifyToken.copyOf())
//
//        val encryptionData = EncryptionRequestPacket.EncryptionData(id, publicKey, verifyToken)
//        client.writePacket(EncryptionRequestPacket, encryptionData)

        client.writePacket(LoginSuccessPacket, EmptyPacketData)

        client.currentState = State.Play

        val nbt = NBTCompound()
        val overworld = NBTCompound()
            .setFloat("ambient_light", 0f)
            .setString("infiniburn", "minecraft:infiniburn_overworld")
            .setByte("natural", 1)
            .setByte("has_ceiling", 0)
            .setByte("has_skylight", 1)
            .setByte("ultrawarm", 0)
            .setByte("has_raids", 1)
            .setByte("respawn_anchor_works", 0)
            .setByte("bed_works", 1)
            .setByte("piglin_safe", 0)
            .setInt("logical_height", 256)
            .setInt("coordinate_scale", 1)

        val dimensions = NBTCompound().apply {
            setString("type", "minecraft:dimension_type")
            val list = NBTList<NBTCompound>(NBTTypes.TAG_Compound)
            val dimension = NBTCompound()
            dimension.setString("name", "minecraft:overworld")
            dimension.setInt("id", 0)

            dimension["element"] = overworld
            list.add(dimension)
            set("value", list)
        }

        val biomes = NBTCompound().apply {
            setString("type", "minecraft:worldgen/biome")
            val list = NBTList<NBTCompound>(NBTTypes.TAG_Compound)

            val plainsBiome = NBTCompound()
            plainsBiome.setString("name", "minecraft:plains")
            plainsBiome.setInt("id", 0)

            val element = NBTCompound()
            element.setFloat("depth", 0.125f)
            element.setFloat("temperature", 0.8f)
            element.setFloat("scale", 0.05f)
            element.setFloat("downfall", 0.4f)
            element.setString("category", "none")
            element.setString("precipitation", "rain")
            element["effects"] = NBTCompound().apply {
                setInt("fog_color", 12638463)
                setInt("sky_color", 7907327)
                setInt("water_fog_color", 329011)
                setInt("water_color", 4159204)
            }
            plainsBiome["element"] = element

            list.add(plainsBiome)
            set("value", list)
        }

        nbt["minecraft:dimension_type"] = dimensions
        nbt["minecraft:worldgen/biome"] = biomes


        val joinGameData = JoinGamePacket.JoinGameData(
            eid = 0,
            hardcore = false,
            gameMode = 0,
            previousGameMode = -1,
            worlds = listOf(Identifier("world")),
            dimensionCodec = nbt,
            dimension = overworld,
            worldName = Identifier("world"),
            hashedSeed = 0,
            maxPlayers = 0, //unused
            viewDistance = 8,
            reducedDebugInfo = false,
            respawnScreen = true,
            debug = false,
            flat = false
        )

        client.writePacket(JoinGamePacket, joinGameData)
    }

    const val VERIFY_TOKEN_KEY = "mk_verify_token"
}
