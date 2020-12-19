package me.bristermitten.minekraft

import com.soywiz.korio.async.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import me.bristermitten.minekraft.server.PacketServer

class Server(val port: Int = 25565) {
    fun start(): Job {
        val job = launch(Dispatchers.IO) {
            PacketServer().start(port)
        }
        println("Bound to $port")
        return job
    }
}

fun main() {
    runBlocking {
        Server().start().join()
    }
}
