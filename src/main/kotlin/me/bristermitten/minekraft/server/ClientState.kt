package me.bristermitten.minekraft.server

import java.net.SocketAddress
import java.util.concurrent.ConcurrentHashMap

object ClientState {
    private val states = ConcurrentHashMap<SocketAddress, Map<String, Any>>()

    fun getStates(address: SocketAddress): Map<String, Any> {
        return states.getOrPut(address, ::mapOf)
    }

    fun getState(address: SocketAddress, key: String): Any? {
        val states = getStates(address)
        return states[key]
    }

    fun setState(address: SocketAddress, key: String, value: Any) {
        val states = getStates(address)
        val mutStates = states.toMutableMap()
        mutStates[key] = value
        this.states[address] = mutStates
    }

    fun clear(address: SocketAddress) {
        states.remove(address)
    }
}
