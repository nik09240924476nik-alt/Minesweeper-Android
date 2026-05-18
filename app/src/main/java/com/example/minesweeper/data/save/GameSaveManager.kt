package com.example.minesweeper.data.save

import android.content.Context
import android.content.SharedPreferences

class GameSaveManager(
    context: Context
) {

    private val prefs: SharedPreferences =

        context.getSharedPreferences(
            "game_save",
            Context.MODE_PRIVATE
        )

    fun saveDifficulty(
        difficulty: String
    ) {

        prefs.edit()

            .putString(
                "difficulty",
                difficulty
            )

            .apply()
    }

    fun loadDifficulty(): String {

        return prefs.getString(
            "difficulty",
            "Легко"
        ) ?: "Легко"
    }

    fun saveTimer(
        time: Int
    ) {

        prefs.edit()

            .putInt(
                "timer",
                time
            )

            .apply()
    }

    fun loadTimer(): Int {

        return prefs.getInt(
            "timer",
            0
        )
    }
}