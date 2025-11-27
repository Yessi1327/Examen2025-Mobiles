package com.app.examen2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.examen2025.presentation.screens.home.HomeScreen
import com.app.examen2025.presentation.screens.sudoku.SudokuDetailScreen

// Sección 1 Define todas las rutas de navegación en un solo lugar

// Cada objeto dentro representa una pantalla con su propia ruta.
sealed class Screen(
    // Propiedad que almacena el nombre o patrón de la ruta
    val route: String,
) {
    // Este objeto define una ruta para la pantalla de inicio
    object Home : Screen("home")

    object Detail : Screen("sudoku/{size}/{difficulty}") {
        fun createRoute(
            size: Int,
            difficulty: String,
        ): String = "sudoku/$size/$difficulty"
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuNavGraph(
    modifier: Modifier = Modifier,
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
                onStartClick = { size, difficulty ->
                    navController.navigate(
                        Screen.Detail.createRoute(size, difficulty),
                    )
                },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments =
                listOf(
                    navArgument("size") { type = NavType.IntType },
                    navArgument("difficulty") { type = NavType.StringType },
                ),
        ) { backStackEntry ->
            val size = backStackEntry.arguments?.getInt("size") ?: 9
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "medium"

            SudokuDetailScreen(
                size = size,
                difficulty = difficulty,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
