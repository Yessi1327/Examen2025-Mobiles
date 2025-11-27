package com.app.examen2025.data.remote.api

import com.app.examen2025.data.remote.dto.SudokuDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SudokuApi {
    @GET("sudokugenerate")
    suspend fun generateSudoku(
        // Header con API key
        @Header("X-Api-Key") apiKey: String,
        @Query("width") width: Int,
        @Query("height") height: Int,
        // Dificultad
        @Query("easy") difficulty: String,
    ): SudokuDto
}
