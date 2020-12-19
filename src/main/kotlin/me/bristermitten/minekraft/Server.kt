package me.bristermitten.minekraft

import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bristermitten.minekraft.server.PacketServer

class Server(val port: Int = 25565) {
    fun start() {
        launch(Dispatchers.IO) {
            PacketServer().start(port)
        }
        println("Bound to $port")
    }
}

fun main() {
    Server().start()
    while (true) {
    }
}
