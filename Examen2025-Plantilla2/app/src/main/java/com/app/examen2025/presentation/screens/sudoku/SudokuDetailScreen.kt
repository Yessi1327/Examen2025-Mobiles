package com.app.examen2025.presentation.screens.sudoku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.examen2025.presentation.common.ErrorView
import com.app.examen2025.presentation.common.LoadingShimmer
import com.app.examen2025.presentation.screens.sudoku.components.NumberPickerDialog
import com.app.examen2025.presentation.screens.sudoku.components.SudokuBoard

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuDetailScreen(
    size: Int,
    difficulty: String,
    onBackClick: () -> Unit,
    viewModel: SudokuDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(size, difficulty) {
        viewModel.loadSudoku(size, difficulty)
    }

    // Estado local
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var showNumberDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sudoku ${size}x$size ($difficulty)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
        ) {
            when {
                uiState.isLoading -> {
                    LoadingShimmer(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .align(Alignment.TopCenter),
                    )
                }

                uiState.error != null -> {
                    ErrorView(
                        message = uiState.error ?: "Error desconocido",
                        onRetry = { viewModel.loadSudoku(size, difficulty) },
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                uiState.sudoku != null && uiState.currentBoard.isNotEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Completa el Sudoku y presiona \"Verificar solución\"",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            SudokuBoard(
                                uiState = uiState,
                                onCellClick = { row, col ->
                                    val sudoku = uiState.sudoku ?: return@SudokuBoard

                                    // No permitir clicks en pistas
                                    if (sudoku.puzzle[row][col] != null) return@SudokuBoard

                                    selectedCell = row to col
                                    showNumberDialog = true
                                },
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            if (uiState.verificationMessage != null) {
                                Text(
                                    text = uiState.verificationMessage!!,
                                    style =
                                        MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                    color =
                                        if (uiState.verificationMessage!!.startsWith("¡Bien Hecho ejjeje")) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.error
                                        },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    androidx.compose.foundation.layout.Arrangement
                                        .spacedBy(8.dp),
                            ) {
                                Button(
                                    onClick = { viewModel.verifySolution() },
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text("Verificar solución")
                                }

                                Button(
                                    onClick = { viewModel.resetBoard() },
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text("Reiniciar")
                                }
                            }
                        }
                    }
                }
            }

            // Dialog
            if (showNumberDialog && selectedCell != null && uiState.sudoku != null) {
                val (row, col) = selectedCell!!

                NumberPickerDialog(
                    size = uiState.sudoku!!.size,
                    onNumberSelected = { value ->
                        viewModel.onCellValueChanged(row, col, value)
                        showNumberDialog = false
                    },
                    onDismiss = {
                        showNumberDialog = false
                    },
                )
            }
        }
    }
}
