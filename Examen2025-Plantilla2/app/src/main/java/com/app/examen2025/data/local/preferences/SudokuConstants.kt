package com.app.examen2025.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.app.examen2025.data.local.model.SudokuCache
import com.app.examen2025.domain.model.Sudoku
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
        private val gson: Gson,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(
                PreferencesConstants.PREF_NAME,
                Context.MODE_PRIVATE,
            )

        // Guardar un Sudoku caheton
        fun saveSudoku(sudoku: Sudoku) {
            prefs
                .edit()
                .putString(
                    PreferencesConstants.KEY_SUDOKU_CACHE,
                    gson.toJson(sudoku),
                ).putLong(
                    PreferencesConstants.KEY_LAST_UPDATE,
                    System.currentTimeMillis(),
                ).apply()
        }

        // Recupera el cache si existe
        fun getSudokuCache(): SudokuCache? {
            val json = prefs.getString(PreferencesConstants.KEY_SUDOKU_CACHE, null)
            val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0L)

            if (json == null) return null

            // Gson convierte el JSON al tipo Sudoku
            val sudoku: Sudoku = gson.fromJson(json, Sudoku::class.java)

            return SudokuCache(
                sudoku = sudoku,
                lastUpdate = lastUpdate,
            )
        }

        // Validar si la cache
        fun isCacheValid(): Boolean {
            val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0L)

            return System.currentTimeMillis() - lastUpdate < PreferencesConstants.CACHE_DURATION
        }

        // Limpia Cahe
        fun clearCache() {
            prefs.edit().clear().apply()
        }
    }
