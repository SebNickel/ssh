package org.seb.ssh

import org.apache.sshd.client.SshClient
import org.apache.sshd.client.channel.ClientChannel
import org.apache.sshd.common.util.io.NoCloseInputStream
import org.apache.sshd.common.util.io.NoCloseOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyPair

class OpenChannel(private val client: SshClient = SshClient.setUpDefaultClient()) {

    fun apply(username: String,
              hostname: String,
              passphrase: String,
              keyPair: KeyPair,
              inputStream: InputStream,
              outputStream: OutputStream,
              errorStream: OutputStream): Pair<SshClient, ClientChannel> {

        client.start()

        val session = client.connect(username, hostname, 22).await().session

        session.addPublicKeyIdentity(keyPair)
        session.addPasswordIdentity(passphrase)

        session.auth().verify()

        val channel = session.createChannel("shell")

        channel.setIn(NoCloseInputStream(inputStream))
        channel.setOut(NoCloseOutputStream(outputStream))
        channel.setErr(NoCloseOutputStream(errorStream))

        channel.open()

        return Pair(client, channel)

    }

}