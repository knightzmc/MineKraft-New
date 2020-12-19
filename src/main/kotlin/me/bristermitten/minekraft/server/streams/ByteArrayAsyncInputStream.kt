package me.bristermitten.minekraft.server.streams

import com.soywiz.korio.stream.AsyncInputStream

class ByteArrayAsyncInputStream(private val bytes: ByteArray, private val source: AsyncInputStream) : AsyncInputStream {
    private var cursor = 0

    override suspend fun close() {
        source.close()
    }

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        if (cursor >= bytes.size) {
            return source.read(buffer, offset, len)
        }
        for (n in 0 until len) {
            buffer[offset + n] = bytes[offset + n + (cursor++)]
            if (cursor > bytes.size) {
                return source.read(buffer, offset, len - n)
            }
        }
        return 0
    }
}
