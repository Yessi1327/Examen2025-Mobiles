package com.app.examen2025.presentation.screens.sudoku.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun NumberPickerDialog(
    size: Int,
    onNumberSelected: (Int?) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Selecciona un num")
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Números válidos según el tamaño
                val numbers = (1..size).toList()
                // Los partimos en filas de máximo 5 números para que se vean
                val rows = numbers.chunked(5)

                rows.forEach { rowNumbers ->
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    ) {
                        rowNumbers.forEach { number ->
                            Button(
                                onClick = { onNumberSelected(number) },
                            ) {
                                Text(
                                    text = number.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                TextButton(
                    onClick = { onNumberSelected(null) },
                ) {
                    Text("Borrar celda")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
    )
}
