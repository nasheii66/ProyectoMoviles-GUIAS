package com.example.app.navigation

/**
 * Eventos de Navegación (Sección 3.B - Guía 10).
 * Abstrae las acciones sobre el backstack para desacoplar la UI del NavController.
 */
sealed class NavigationEvent {

    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    data object PopBackStack : NavigationEvent()

    data object NavigateUp : NavigationEvent()
}
