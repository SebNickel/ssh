package org.seb.ssh

import org.apache.sshd.client.channel.ClientChannel

object KeepAlive {

    fun apply(channel: ClientChannel) = channel.waitFor(ClientChannel.CLOSED, 0)

}
