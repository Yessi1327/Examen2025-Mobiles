package com.app.examen2025.presentation.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor() : ViewModel() {
        // Estado interno mutable
        private val _uiState = MutableStateFlow(HomeUiState())

        // Estado expuesto solo de lectura
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        // Cambiar tamaño
        fun onSizeSelected(size: Int) {
            _uiState.update { state ->
                state.copy(selectedSize = size)
            }
        }

        // Cambiar dificultad
        fun onDifficultySelected(difficulty: String) {
            _uiState.update { state ->
                state.copy(selectedDifficulty = difficulty)
            }
        }
    }
