package me.bristermitten.minekraft.encoding

import com.soywiz.korio.stream.AsyncOutputStream
import me.bristermitten.minekraft.server.streams.writeByte
import kotlin.experimental.or


suspend fun AsyncOutputStream.writeVarInt(int: Int) {
    var value = int
    do {
        var temp = (value and 127).toByte()
        value = value ushr 7
        if (value != 0) {
            temp = temp or 128.toByte()
        }
        writeByte(temp)
    } while (value != 0)
}


fun getVarIntSize(input: Int): Int {
    return when {
        input and -0x80 == 0 -> 1
        input and -0x4000 == 0 -> 2
        input and -0x200000 == 0 -> 3
        input and -0x10000000 == 0 -> 4
        else -> 5
    }
}
