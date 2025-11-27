package com.app.examen2025.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun LoadingShimmer(modifier: Modifier = Modifier) {
    // Los colores para la animación
    val shimmerColors =
        listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )
    // La animación
    // rememberInfiniteTransition: Crea una transición que se repite infinitamente
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim =
        // animateFloat: Anima un valor float desde 0 hasta 1000
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            // infiniteRepeatable: Hace que la animación se repita indefinidamente
            animationSpec =
                infiniteRepeatable(
                    // tween(1000): Define que la animación dura 1 segundo (1000ms)
                    animation = tween(1000),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "",
        )

    // El gradiente:El gradiente se mueve diagonalmente gracias a los valores animados de x e y
    val brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnim.value, y = translateAnim.value),
        )

    // La UI
    // Usamos un Surface con esquinas redondeadas
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        // El Spacer ocupa todo el espacio disponible y
        // muestra nuestro gradiente animado
        Spacer(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(brush),
        )
    }
}
