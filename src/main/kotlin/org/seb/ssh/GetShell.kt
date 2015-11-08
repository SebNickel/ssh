package org.seb.ssh

import org.apache.sshd.client.SshClient
import org.apache.sshd.client.session.ClientSession
import org.apache.sshd.common.util.io.NoCloseInputStream
import org.apache.sshd.common.util.io.NoCloseOutputStream
import java.security.KeyPair

object GetShell {

    fun apply(username: String,
              hostname: String,
              passphrase: String,
              keyPair: KeyPair) {

        val client = SshClient.setUpDefaultClient()

        client.start()

        val session = client.connect(username, hostname, 22).await().session

        session.addPublicKeyIdentity(keyPair)
        session.addPasswordIdentity(passphrase)

        session.auth().verify()

        val channel = session.createChannel("shell")

        channel.setIn(NoCloseInputStream(System.`in`))
        channel.setOut(NoCloseOutputStream(System.out))
        channel.setErr(NoCloseOutputStream(System.err))

        channel.open()

        channel.waitFor(ClientSession.CLOSED, 0)

        session.close(false)

        client.stop()

    }

}