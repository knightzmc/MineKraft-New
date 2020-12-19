package me.bristermitten.minekraft.server

import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.net.JvmAsyncClient
import java.net.SocketAddress
import java.nio.channels.AsynchronousSocketChannel


private val jvmScField by lazy {
    val field = JvmAsyncClient::class.java.getDeclaredField("sc")
    field.isAccessible = true
    field
}

fun AsyncClient.getAddress(): SocketAddress {
    val jvm = this as JvmAsyncClient
    if (!jvm.connected) {
        throw IllegalArgumentException("Not connected")
    }

    val sc = jvmScField[jvm] as AsynchronousSocketChannel? ?: throw IllegalArgumentException("SC field is null")
    return sc.remoteAddress
}
