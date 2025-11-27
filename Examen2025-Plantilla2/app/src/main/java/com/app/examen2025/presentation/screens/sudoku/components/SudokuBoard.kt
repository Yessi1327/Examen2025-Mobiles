package com.app.examen2025.presentation.screens.sudoku.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.examen2025.presentation.screens.sudoku.SudokuDetailUiState

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuBoard(
    uiState: SudokuDetailUiState,
    onCellClick: (row: Int, col: Int) -> Unit,
) {
    val sudoku = uiState.sudoku ?: return
    val board = uiState.currentBoard

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            board.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    row.forEachIndexed { colIndex, cellValue ->
                        val isClue = sudoku.puzzle[rowIndex][colIndex] != null

                        SudokuCell(
                            value = cellValue,
                            isClue = isClue,
                            onClick = { onCellClick(rowIndex, colIndex) },
                        )
                    }
                }
            }
        }
    }
}
