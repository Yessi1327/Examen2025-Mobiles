package com.app.examen2025.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.usecase.GetSudokuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuDetailViewModel
    @Inject
    constructor(
        private val getSudokuUseCase: GetSudokuUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuDetailUiState())
        val uiState: StateFlow<SudokuDetailUiState> = _uiState.asStateFlow()

        // LLamada Al UsE Case

        fun loadSudoku(
            size: Int,
            difficulty: String,
        ) {
            viewModelScope.launch {
                getSudokuUseCase(size, difficulty).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                    error = null,
                                    verificationMessage = null,
                                )

                            is Result.Success -> {
                                val sudoku = result.data
                                state.copy(
                                    sudoku = sudoku,
                                    currentBoard = sudoku.puzzle, // se inicia igual que el puzzle
                                    isLoading = false,
                                    error = null,
                                    verificationMessage = null,
                                )
                            }

                            is Result.Error ->
                                state.copy(
                                    isLoading = false,
                                    error =
                                        result.exception.message
                                            ?: "Ocurrió un error al cargar el sudoku",
                                    verificationMessage = null,
                                )
                        }
                    }
                }
            }
        }

        // Cambiar el SudokuCell

        fun onCellValueChanged(
            row: Int,
            col: Int,
            value: Int?,
        ) {
            val currentState = _uiState.value
            val sudoku = currentState.sudoku ?: return

            // No permitir modificar las celdas con pistas
            if (sudoku.puzzle[row][col] != null) {
                return
            }

            _uiState.update { state ->
                val newBoard =
                    state.currentBoard.mapIndexed { r, rowList ->
                        rowList.mapIndexed { c, cell ->
                            if (r == row && c == col) {
                                value
                            } else {
                                cell
                            }
                        }
                    }

                state.copy(
                    currentBoard = newBoard,
                    verificationMessage = null,
                )
            }
        }

        // Verificar La Solucion quen me dio lla Api
        fun verifySolution() {
            val state = _uiState.value
            val sudoku = state.sudoku ?: return
            val current = state.currentBoard

            if (current.isEmpty()) return

            val correct =
                current.size == sudoku.solution.size &&
                    current.indices.all { r ->
                        current[r].size == sudoku.solution[r].size &&
                            current[r].indices.all { c ->
                                val v = current[r][c]
                                // Debe estar llena (no null) y coincidir con la solution
                                v != null && v == sudoku.solution[r][c]
                            }
                    }

            _uiState.update {
                it.copy(
                    verificationMessage =
                        if (correct) {
                            "¡Felicidades! La solución es correcta."
                        } else {
                            "La solución todavía no es correcta. Revisa de nuevo el tablero."
                        },
                )
            }
        }

        // Reiniciar
        fun resetBoard() {
            val sudoku = _uiState.value.sudoku ?: return

            _uiState.update {
                it.copy(
                    currentBoard = sudoku.puzzle, // volvemos al puzzle original
                    verificationMessage = null,
                )
            }
        }

        // Opcional: limpiar mensaje de error desde la UI
        fun clearError() {
            _uiState.update {
                it.copy(error = null)
            }
        }
    }
