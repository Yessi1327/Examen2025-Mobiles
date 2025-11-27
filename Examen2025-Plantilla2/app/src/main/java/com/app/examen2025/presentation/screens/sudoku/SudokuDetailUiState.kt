package com.app.examen2025.presentation.screens.sudoku

import com.app.examen2025.domain.model.Sudoku

data class SudokuDetailUiState(
    // Sudoku que viene del API
    val sudoku: Sudoku? = null,
    // Tablero actual que el usuario está editando.
    val currentBoard: List<List<Int?>> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val verificationMessage: String? = null,
)
