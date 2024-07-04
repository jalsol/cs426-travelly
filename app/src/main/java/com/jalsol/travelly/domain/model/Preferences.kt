package com.jalsol.travelly.domain.model

import kotlinx.serialization.Serializable
import java.io.InputStream
import java.io.OutputStream


@Serializable
object Preferences {

    var email = ""
    var phone = ""
    var firstName = ""
    var lastname = ""

    val defaultInstance: Preferences
        get() = Preferences

    fun parseFrom(input: InputStream): Preferences {
        TODO()
    }

    fun writeTo(output: OutputStream) {
        TODO()
    }
}