package org.seb.ssh

import com.beust.jcommander.Parameter

class Params {

    @Parameter(
        names = arrayOf("-u", "--username"),
        required = true
    )
    val username: String? = null

    @Parameter(
        names = arrayOf("-h", "--hostname"),
        required = true
    )
    val hostname: String? = null

    @Parameter(
        names = arrayOf("-k", "--key"),
        description = "The path to the pem file.",
        required = true
    )
    val keyPath: String? = null

}