package me.bristermitten.minekraft.encryption

import java.util.*

fun ByteArray.base64Encode(): ByteArray {
    return Base64.getEncoder().encode(this)
}

fun ByteArray.base64Decode(): ByteArray {
    return Base64.getDecoder().decode(this)
}
