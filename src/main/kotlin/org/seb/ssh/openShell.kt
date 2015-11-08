package org.seb.ssh

import com.beust.jcommander.JCommander
import java.io.File
import java.io.FileReader

// Unfortunately the resulting shell is pretty bad.

fun main(args: Array<String>) {

    val params = Params()
    JCommander(params, *args)

    val keyPath = params.keyPath

    val pemReader = FileReader(keyPath)

    val keyPair = ReadPemFile.apply(pemReader)

    val keyName = File(keyPath).toPath().fileName

    print("Passphrase for $keyName: ")

    val passphrase = readLine()!!

    val pair =
        OpenChannel().apply(
            params.username!!,
            params.hostname!!,
            passphrase,
            keyPair,
            System.`in`,
            System.out,
            System.err
        )

    val client = pair.first
    val channel = pair.second

    KeepAlive.apply(channel)

    channel.close(false)

    client.stop()

}