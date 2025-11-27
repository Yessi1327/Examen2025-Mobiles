package com.app.examen2025.domain.common

// Este archivo nos permitirá definir estados para las respuestas
// de nuestra API, y manejar mejor la interfaz según sea el caso
sealed class Result<out T> {
    // Loading no necesita datos, siempre es el mismo estado
    object Loading : Result<Nothing>()

    // data class es para clases que solo contienen datos
    data class Success<T>(
        val data: T,
    ) : Result<T>()

    // Loading no retorna datos, por eso usa Nothing
    // Se usa para funciones que nunca terminan o siempre lanzan excepción
    data class Error(
        val exception: Throwable,
    ) : Result<Nothing>()
}
