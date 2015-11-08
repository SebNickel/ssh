package org.seb.ssh

import com.beust.jcommander.JCommander
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {

    val params = Params()
    JCommander(params, *args)

    val keyPath = params.keyPath

    val pemReader = FileReader(keyPath)

    val keyPair = ReadPemFile.apply(pemReader)

    val keyName = File(keyPath).toPath().fileName

    print("Passphrase for $keyName: ")

    val passphrase = readLine()!!

    GetShell.apply(
        params.username!!,
        params.hostname!!,
        passphrase,
        keyPair
    )

}