package com.app.examen2025.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    onStartClick: (size: Int, difficulty: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sizeOptions = listOf(4, 9)
    val difficultyOptions = listOf("easy", "medium", "hard")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sudoku App") },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                //  Tamaño del tablero
                Text(text = "Tamaño del tablero")
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    sizeOptions.forEach { size ->
                        FilterChip(
                            selected = uiState.selectedSize == size,
                            onClick = { viewModel.onSizeSelected(size) },
                            label = { Text(text = "${size}x$size") },
                            modifier = Modifier.weight(1f),
                            leadingIcon =
                                if (uiState.selectedSize == size) {
                                    {
                                        Text("✓")
                                    }
                                } else {
                                    null
                                },
                            colors = FilterChipDefaults.filterChipColors(),
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(top = 24.dp))

                //  Dificultad
                Text(text = "Dificultad")
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    difficultyOptions.forEach { difficulty ->
                        val isSelected = uiState.selectedDifficulty == difficulty

                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onDifficultySelected(difficulty) },
                            label = { Text(text = difficulty.replaceFirstChar { it.uppercase() }) },
                            modifier = Modifier.weight(1f),
                            leadingIcon =
                                if (isSelected) {
                                    { Text("✓") }
                                } else {
                                    null
                                },
                        )
                    }
                }
            }

            //  Botón para comenzar a llamar a Detail
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text =
                        "Tablero: ${uiState.selectedSize}x${uiState.selectedSize} · " +
                            "Dificultad: ${uiState.selectedDifficulty}",
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))

                Button(
                    onClick = {
                        onStartClick(
                            uiState.selectedSize,
                            uiState.selectedDifficulty,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Comenzar")
                }
            }
        }
    }
}
