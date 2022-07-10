package demo.kx.extensions

import java.util.UUID


fun String.uuid(): String = UUID.nameUUIDFromBytes(this.encodeToByteArray()).toString()
