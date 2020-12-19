package me.bristermitten.minekraft.data

data class Identifier(val namespace: String, val value: String) {
    constructor(value: String) : this("minecraft", value)

    init {
        require(NAMESPACE_PATTERN.matches(namespace)) { "Invalid namespace $namespace" }
    }

    val stringValue
        get() = "$namespace:$value"

    companion object {
        private val NAMESPACE_PATTERN = "[0-9a-z-_]+".toRegex()
    }
}
