package me.bristermitten.minekraft.server.streams

import com.soywiz.korio.stream.AsyncInputStream
import com.soywiz.korio.stream.readBytesExact
import com.soywiz.korio.stream.readS8
import kotlin.experimental.and


suspend fun AsyncInputStream.readByte(): Byte = readS8().toByte()

suspend fun AsyncInputStream.readVarInt(): Int {
    var numRead = 0
    var result = 0
    var read: Byte
    do {
        read = readByte()
        val value = (read and 127).toInt()
        result = result or (value shl 7 * numRead)
        numRead++
        if (numRead > 5) {
            throw RuntimeException("VarInt is too big")
        }
    } while (read and 128.toByte() != 0.toByte())

    return result
}

suspend fun AsyncInputStream.readMCString(maxSize: Short = Short.MAX_VALUE): String {
    val len = readVarInt()
    if (len > maxSize) {
        throw IllegalArgumentException("String is too long - max size = $maxSize, string length = $len")
    }
    val array = this.readBytesExact(len)
    return String(array, Charsets.UTF_8)
}
