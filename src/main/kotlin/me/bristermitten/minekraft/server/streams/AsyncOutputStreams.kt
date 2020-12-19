package me.bristermitten.minekraft.server.streams

import com.soywiz.korio.stream.AsyncOutputStream
import com.soywiz.korio.stream.write64BE
import com.soywiz.korio.stream.writeBytes
import me.bristermitten.minekraft.data.Identifier
import me.bristermitten.minekraft.data.NBT
import me.bristermitten.minekraft.encoding.writeVarInt
import java.util.*

suspend fun AsyncOutputStream.writeByte(byte: Byte) = write(byte.toInt())

suspend fun AsyncOutputStream.writeMCString(string: String, maxSize: Short = Short.MAX_VALUE) {
    val bytes = string.toByteArray(Charsets.UTF_8)
    if (bytes.size > maxSize) {
        throw IllegalArgumentException("String too big (was " + bytes.size + " bytes encoded, max " + maxSize + ")")
    }
    writeVarInt(bytes.size)
    writeBytes(bytes)
}

suspend fun AsyncOutputStream.writeUUID(uuid: UUID) {
    write64BE(uuid.mostSignificantBits)
    write64BE(uuid.leastSignificantBits)
}

suspend fun AsyncOutputStream.writeBoolean(boolean: Boolean) {
    write(if (boolean) 1 else 0)
}

suspend fun AsyncOutputStream.writeIdentifier(identifier: Identifier) {
    writeMCString(identifier.stringValue)
}

suspend fun AsyncOutputStream.writeNBT(nbt: NBT) {
    nbt.push(this)
}

suspend fun AsyncOutputStream.writePosition(x: Int, y: Int, z: Int) {
    val asLong = x.toLong() and 0x3FFFFFF shl 38 or (z.toLong() and 0x3FFFFFF shl 12) or (y.toLong() and 0xFFF)
    write64BE(asLong)
}
