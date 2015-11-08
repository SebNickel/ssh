package org.seb.ssh

import java.io.ByteArrayOutputStream

object StreamCommands {

    fun apply(commands: Iterable<String>,
              stream: ByteArrayOutputStream) {

        val commandBytes = commands.map { it.toByteArray() }

        commandBytes.forEach { stream.write(it) }

        stream.flush()

    }

}