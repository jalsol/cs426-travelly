package com.jalsol.travelly.domain.serializer

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.jalsol.travelly.domain.model.Preferences
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore by dataStore("preferences.json", PreferencesSerializer)

object PreferencesSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences
        get() = Preferences()

    override suspend fun readFrom(input: InputStream): Preferences {
        return try {
            Json.decodeFromString(
                deserializer = Preferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Preferences.serializer(),
                value = t
            ).toByteArray()
        )
    }
}
