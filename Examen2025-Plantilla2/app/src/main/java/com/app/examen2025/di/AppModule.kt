package com.app.examen2025.di

import android.content.Context
import com.app.examen2025.data.remote.api.SudokuApi
import com.app.examen2025.data.repository.SudokuRepositoryImpl
import com.app.examen2025.domain.repository.SudokuRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
// import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            // Crea un constructor para configurar Retrofit.
            .Builder()
            // URL base de la API.
            .baseUrl("https://api.api-ninjas.com/v1/")
            // Usa Gson para convertir JSON a objetos
            .addConverterFactory(GsonConverterFactory.create())
            // Finalemente, Construye la instancia de Retrofit
            .build()

    // GSON es una libreía externa, recuerda que todas las librerias no manejan Inyeccion
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    // Solo existira una instancia compartida en toda la app
    @Provides
    @Singleton
    fun provideSudokuApi(retrofit: Retrofit): SudokuApi = retrofit.create(SudokuApi::class.java)

    // HoroscopePreferences
    /*@Provides
    @Singleton
    fun provideSudokuPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): SudokuPreferences = SudokuPreferences(context, gson)
*/
    // API key de api-ninjas
    @Provides
    @Singleton
    @Named("sudokuApiKey")
    fun provideSudokuApiKey(): String = "wLVPN1zV08lJYF7uXqgyPw==zVwp6TlVcAO1NLUf" // SUSTITÚYELA

    // Retorna una implementación concreta del repositorio, usando la API
    @Provides
    @Singleton
    fun provideSudokuRepository(
        api: SudokuApi,
        // preferences: SudokuPreferences,
        @Named("sudokuApiKey") apiKey: String,
    ): SudokuRepository =
        SudokuRepositoryImpl(api, /*preferences,*/ apiKey)
}
