package me.bristermitten.minekraft.server.streams

import com.soywiz.korio.stream.AsyncOutputStream
import java.nio.ByteBuffer

class BufferedAsyncOutputStream(size: Int = 2048) : AsyncOutputStream {
    private val buf = ByteBuffer.allocate(size)
    override suspend fun close() {
        //No-op
    }

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        buf.put(buffer, offset, len)
    }

    val len get() = buf.position()

    val bytes: ByteArray
        get() =
            if (buf.hasArray()) buf.array().sliceArray(0 until len)
            else {
                val bytes = ByteArray(len)
                buf.rewind()
                buf.get(bytes)
                bytes
            }
}
