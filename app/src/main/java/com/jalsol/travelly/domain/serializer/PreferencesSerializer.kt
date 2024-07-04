package com.jalsol.travelly.domain.serializer

import androidx.datastore.core.Serializer
import com.jalsol.travelly.domain.model.Preferences
import java.io.InputStream
import java.io.OutputStream

object PreferencesSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences = Preferences.defaultInstance

    override suspend fun readFrom(input: InputStream): Preferences {
        return try {
            Preferences.parseFrom(input)
        } catch (e: Exception) {
            Preferences.defaultInstance
        }
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) = t.writeTo(output)
}