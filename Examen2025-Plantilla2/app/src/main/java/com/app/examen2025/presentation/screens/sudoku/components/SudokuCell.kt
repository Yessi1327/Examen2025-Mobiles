package com.app.examen2025.presentation.screens.sudoku.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuCell(
    value: Int?,
    isClue: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor =
        if (isClue) {
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
        } else {
            MaterialTheme.colorScheme.surface
        }

    val textColor =
        if (isClue) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    Box(
        modifier =
            Modifier
                .padding(2.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor)
                .then(
                    if (!isClue) Modifier.clickable { onClick() } else Modifier,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            colors =
                CardDefaults.cardColors(
                    containerColor = backgroundColor,
                ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = value?.toString() ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                )
            }
        }
    }
}
