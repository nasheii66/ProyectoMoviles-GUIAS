package com.example.app.navigation

/**
 * Definición de Rutas Tipo-Safe (Sección 3.A - Guía 10).
 * Implementada mediante una sealed class Screen(val route: String).
 */
sealed class Screen(val route: String) {

    // Rutas simples sin parámetros declaradas como data object (Kotlin 1.9+)
    data object Home : Screen("home_page")
    data object Profile : Screen("profile_page")
    data object Settings : Screen("settings_page")

    // Rutas parametrizadas tipadas mediante data class
    data class Detail(val itemId: String) : Screen("detail_page/{itemId}") {
        fun buildRoute(): String {
            return "detail_page/$itemId"
        }

        companion object {
            const val ROUTE_PATTERN = "detail_page/{itemId}"
            const val ARG_ITEM_ID = "itemId"
        }
    }
}
