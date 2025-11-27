package com.app.examen2025.presentation.screens.home

import com.app.examen2025.domain.model.Sudoku

// Estado completo de la pantalla Home de Horóscopos
data class HomeUiState(
    val selectedSize: Int = 4,
    val selectedDifficulty: String = "easy",
    // Para mostrar un indicador de carga
    val isLoading: Boolean = false,
    // Mensaje de error si algo falla
    val error: String? = null,
)
