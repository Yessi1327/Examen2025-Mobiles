package com.app.examen2025.data.local.model

import com.app.examen2025.domain.model.Sudoku

data class SudokuCache(
    val sudoku: Sudoku,
    val lastUpdate: Long,
)
