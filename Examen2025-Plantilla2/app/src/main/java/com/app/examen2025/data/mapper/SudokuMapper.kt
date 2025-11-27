package com.app.examen2025.data.mapper

import com.app.examen2025.data.remote.dto.SudokuDto
import com.app.examen2025.domain.model.Sudoku

fun SudokuDto.toDomain(
    size: Int,
    difficulty: String,
): Sudoku =
    Sudoku(
        puzzle = puzzle,
        solution = solution,
        size = size,
        difficulty = difficulty,
    )
