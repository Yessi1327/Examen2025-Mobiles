package com.app.examen2025.domain.repository

import com.app.examen2025.domain.model.Sudoku

interface SudokuRepository {
    suspend fun getSudoku(
        size: Int,
        difficulty: String,
    ): Sudoku
}
