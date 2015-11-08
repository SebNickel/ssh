package org.seb.ssh

import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import java.io.Reader
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

object ReadPemFile {

    private val keyFactory = KeyFactory.getInstance("RSA")

    fun apply(reader: Reader): KeyPair {

        val pemParser = PEMParser(reader)

        val pemKeyPair = pemParser.readObject() as PEMKeyPair

        val publicBytes = pemKeyPair.publicKeyInfo.encoded

        val privateBytes = pemKeyPair.privateKeyInfo.encoded

        val publicSpec = X509EncodedKeySpec(publicBytes)

        val privateSpec = PKCS8EncodedKeySpec(privateBytes)

        val publicKey = keyFactory.generatePublic(publicSpec)

        val privateKey = keyFactory.generatePrivate(privateSpec)

        return KeyPair(publicKey, privateKey)

    }

}