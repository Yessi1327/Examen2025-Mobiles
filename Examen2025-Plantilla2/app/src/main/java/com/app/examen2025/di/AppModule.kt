package com.app.examen2025.di

import android.content.Context
import com.app.examen2025.data.local.preferences.HoroscopePreferences
import com.app.examen2025.data.remote.api.HoroscopeApi
import com.app.examen2025.data.repository.HoroscopeRepositoryImpl
import com.app.examen2025.domain.repository.HoroscopeRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ActivityContext
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
            .baseUrl()
            // Usa Gson para convertir JSON a objetos
            .addConverterFactory(GsonConverterFactory.create())
            // Finalemente, Construye la instancia de Retrofit
            .build()

    // GSON es una libreía externa, recuerda que todas las librerias no manejan Inyeccion
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    // HoroscopeApi
    // Solo existira una instancia compartida en toda la app
    @Provides
    @Singleton
    fun provideHoroscopeApi(
        retrofit: Retrofit,
    ): HoroscopeApi = retrofit.create(HoroscopeApi::class.java)

    // HoroscopePreferences
    @Provides
    @Singleton
    fun provideHoroscopePreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): HoroscopePreferences = HoroscopePreferences(context, gson)

    // API key de api-ninjas
    /*@Provides
    @Singleton
    @Named("horoscopeApiKey")
    fun provideHoroscopeApiKey(): String = "HoV0AQydwZ8Gh4PSq4lKHA==gD3b6CcYBMu8aYEy" // SUSTITÚYELA
    */

    // HoroscopeRepository
    // Retorna una implementación concreta del repositorio, usando la API
    @Provides
    @Singleton
    fun provideHoroscopeRepository(
        api: HoroscopeApi,
        preferences: HoroscopePreferences,
        //@Named("horoscopeApiKey") apiKey: String,
    ): HoroscopeRepository =
        HoroscopeRepositoryImpl(api, preferences, apiKey)
}
