package me.bristermitten.minekraft.data

import com.soywiz.korio.stream.AsyncOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTWriter
import java.io.OutputStream

suspend fun NBT.write(name: String, output: AsyncOutputStream) {
    val out = object : OutputStream() {
        override fun write(b: Int) {
            runBlocking {
                output.write(b)
            }
        }
    }

    withContext(Dispatchers.IO) {
        NBTWriter(out).use {
            it.writeNamed(name, this@write)
        }
    }
}
