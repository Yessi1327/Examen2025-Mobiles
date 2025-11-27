package com.app.examen2025.presentation.screens.home

// Estado completo de la pantalla Home de Horóscopos
data class HomeUiState(
    // Lista de signos zodiacales que se mostrarán en la pantalla principal
    //val signs: List<String> = emptyList(),
    // Para mostrar un indicador de carga (por si en algún momento quieres hacer algo async)
    val isLoading: Boolean = false,
    // Mensaje de error si algo falla (por ahora casi no se usa,
    // pero mantenemos el patrón del laboratorio)
    val error: String? = null,
)
