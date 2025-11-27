package com.app.examen2025.domain.usecase

import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.model.Sudoku
import com.app.examen2025.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSudokuUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(
            size: Int,
            difficulty: String,
        ): Flow<Result<Sudoku>> =
            flow {
                try {
                    // Estado de carga
                    emit(Result.Loading)
                    val puzzle = repository.getSudoku(size, difficulty)
                    emit(Result.Success(puzzle))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
