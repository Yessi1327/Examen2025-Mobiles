package com.app.examen2025.data.repository

import android.util.Log
import com.app.examen2025.data.local.preferences.SudokuPreferences
import com.app.examen2025.data.mapper.toDomain
import com.app.examen2025.data.remote.api.SudokuApi
import com.app.examen2025.domain.model.Sudoku
import com.app.examen2025.domain.repository.SudokuRepository
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuRepositoryImpl
    @Inject
    constructor(
        private val api: SudokuApi,
        private val preferences: SudokuPreferences,
        private val apiKey: String,
    ) : SudokuRepository {
        override suspend fun getSudoku(
            size: Int,
            difficulty: String,
        ): Sudoku {
            val difficultyValue = difficulty.lowercase()

            val (width, height) =
                when (size) {
                    4 -> 2 to 2
                    9 -> 3 to 3
                    else -> 3 to 3 // default a 9x9 por si acaso
                }

            // Obvtener Cavhe
            preferences.getSudokuCache()?.let { cache ->
                if (preferences.isCacheValid() &&
                    cache.sudoku.size == size &&
                    cache.sudoku.difficulty == difficultyValue
                ) {
                    Log.d(
                        "SudokuRepository",
                        "Usando cache (size=$size, diff=$difficultyValue)",
                    )
                    return cache.sudoku
                }
            }

            return try {
                // Llamada al API
                val dto =
                    api.generateSudoku(
                        apiKey = apiKey,
                        width = width,
                        height = height,
                        difficulty = difficultyValue,
                    )

                val sudoku =
                    dto.toDomain(
                        size = size,
                        difficulty = difficultyValue,
                    )

                // Guardar en che
                preferences.saveSudoku(sudoku)

                sudoku
            } catch (e: HttpException) {
                val body = e.response()?.errorBody()?.string()
                Log.e(
                    "SudokuRepository",
                    "HTTP ${e.code()} al generar sudoku size=$size diff=$difficultyValue. Body: $body",
                    e,
                )

                // intenta cache antiguo
                preferences.getSudokuCache()?.sudoku ?: throw e
            } catch (e: Exception) {
                Log.e("SudokuRepository", "Error inesperado al generar sudoku", e)
                preferences.getSudokuCache()?.sudoku ?: throw e

                throw e
            }
        }
    }
