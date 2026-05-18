package com.example.minesweeper.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by
preferencesDataStore("settings")

class SettingsManager(
    private val context: Context
) {

    companion object {

        val SOUND =
            booleanPreferencesKey("sound")

        val VIBRATION =
            booleanPreferencesKey("vibration")

        val AMOLED =
            booleanPreferencesKey("amoled")

        val PARTICLES =
            booleanPreferencesKey("particles")
    }

    val soundFlow: Flow<Boolean> =
        context.dataStore.data.map {

            it[SOUND] ?: true
        }

    val vibrationFlow: Flow<Boolean> =
        context.dataStore.data.map {

            it[VIBRATION] ?: true
        }

    val amoledFlow: Flow<Boolean> =
        context.dataStore.data.map {

            it[AMOLED] ?: false
        }

    val particlesFlow: Flow<Boolean> =
        context.dataStore.data.map {

            it[PARTICLES] ?: true
        }

    suspend fun setSound(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[SOUND] = enabled
        }
    }

    suspend fun setVibration(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[VIBRATION] = enabled
        }
    }

    suspend fun setAmoled(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[AMOLED] = enabled
        }
    }

    suspend fun setParticles(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[PARTICLES] = enabled
        }
    }
}