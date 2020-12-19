package me.bristermitten.minekraft.packet.login

import kotlinx.coroutines.delay
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
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionResponseHandler :
    IncomingPacketHandler<EncryptionResponsePacket, EncryptionResponsePacket.EncryptionResponseData> {

    override suspend fun handle(client: MineKraftClient, data: EncryptionResponsePacket.EncryptionResponseData) {
        //decrypt and compare the verify token
        val nonce = ServerEncryption.rsaDecrypt(data.verifyToken)

        val originalVerifyToken = ClientState.getState(client.address, LoginStartHandler.VERIFY_TOKEN_KEY) as ByteArray

        require(nonce.contentEquals(originalVerifyToken)) {
            "Decrypted verify token was not the same!"
        }

        val secret = ServerEncryption.rsaDecrypt(data.sharedSecret) //Decrypt the shared secret

        val key = SecretKeySpec(secret, "AES")

        val encryptCipher = Cipher.getInstance(ServerEncryption.SHARED_SECRET_ALGORITHM)
        encryptCipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(key.encoded))
        client.encryptionCipher = encryptCipher

        val decryptCipher = Cipher.getInstance(ServerEncryption.SHARED_SECRET_ALGORITHM)
        decryptCipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))
        client.decryptionCipher = decryptCipher



//        client.writePacket(
//            PluginMessageOutPacket, PluginMessageOutPacket.Message(
//                Identifier("brand"),
//                BufferedAsyncOutputStream().apply {
//                    writeMCString("MineKraft")
//                }.bytes
//            )
//        )
    }
}
