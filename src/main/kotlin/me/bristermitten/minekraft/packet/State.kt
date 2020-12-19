package me.bristermitten.minekraft.packet

enum class State(val code: Int) {
    Handshaking(0),
    Status(1),
    Login(2),
    Play(3);

    companion object {
        private val states = values()
        fun fromCode(code: Int) = states[code]
    }
}

