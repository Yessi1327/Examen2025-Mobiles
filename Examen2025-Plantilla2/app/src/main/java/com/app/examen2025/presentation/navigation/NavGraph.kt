package com.app.examen2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.examen2025.domain.model.Horoscope
import com.app.examen2025.presentation.screens.home.HomeScreen
import com.app.examen2025.presentation.screens.horoscope.HoroscopeDetailScreen

// Sección 1 Define todas las rutas de navegación en un solo lugar

// Cada objeto dentro representa una pantalla con su propia ruta.
sealed class Screen(
    // Propiedad que almacena el nombre o patrón de la ruta
    val route: String,
) {
    // Este objeto define una ruta para la pantalla de inicio
    object Home : Screen("home")

    /*// Est define una ruta para la pantalla de detalles de algp
    // Remplazando el argumento con {remplazaId} para datos dinamicos
    object Detail : Screen("horoscope/{sign}") {
        // Función auxiliar que crea la ruta completa reemplazando el valor {remplazaId}
        // con el ID real del Remplazable (por ejemplo, "remplaza/3").
        fun createRoute(sign: String) = "horoscope/$sign"
    }
*/
    // Si quieres meter mas pantallas es aqui
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun HoroscopeNavGraph(
    modifier: Modifier = Modifier,
    // Crea (o recibe) el controlador de navegación
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        // Este se Puede Cambia?
        navController = navController,
        // Define qué pantalla se muestra al abrir la app.
        startDestination = Screen.Home.route,
        // Se puede usar para personalizar el aspecto de la pantalla
        modifier = modifier,
    ) {
        // Primera pantalla: Home

        // "route" es como la dirección de la pantalla
        composable(route = Screen.Home.route) {
            // Llama al composable de la pantalla Home: HomeScreen
            HomeScreen(
                // Parámetro lambda que se ejecuta cuando el usuario toca un algo en la lista.
                onSignClick = { sign ->
                    // Navega a la pantalla de detalle con el ID del remplaza
                    // reemplazando {remplazaId} con el valor real (por ejemplo, "remplaza/1").
                    navController.navigate(Screen.Detail.createRoute(sign))
                },
            )
        }

        /*// Segunda pantalla: Detalle o la que aplique
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("sign") { type = NavType.StringType }),
        ) { backStackEntry ->
            val sign = backStackEntry.arguments?.getString("sign") ?: "aries"

            HoroscopeDetailScreen(
                sign = sign,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }*/
    }
}
