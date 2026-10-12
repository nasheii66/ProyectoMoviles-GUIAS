package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.navigation.NavigationEvent
import com.example.app.navigation.Screen
import com.example.app.ui.components.AppBottomNavigationBar
import com.example.app.ui.components.AppDrawerSheetContent
import com.example.app.ui.components.AppTopBar
import com.example.app.ui.screens.DetailScreen
import com.example.app.ui.screens.HomeScreenAdaptable
import com.example.app.ui.screens.ProfileScreen
import com.example.app.ui.screens.SettingsScreen
import com.example.app.ui.theme.AppProyecto_GrupoXTheme
import com.example.app.ui.utils.LocalActivity
import com.example.app.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Actividad Principal (Sección 3.D y Sección 4 - Guías 7, 8, 9 y 10).
 * Conecta el flujo de eventos de navegación del MainViewModel con el NavController
 * dentro de una estructura con ModalNavigationDrawer, Scaffold, TopAppBar y NavigationBar.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                AppProyecto_GrupoXTheme {
                    MainAppContainer()
                }
            }
        }
    }
}

@Composable
fun MainAppContainer(
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    // Consumo de eventos de navegación desde el ViewModel (Sección 3.D)
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    val destino = when (val route = event.route) {
                        is Screen.Detail -> route.buildRoute()
                        else -> route.route
                    }
                    navController.navigate(destino) {
                        event.popUpToRoute?.let { popRoute ->
                            popUpTo(popRoute.route) {
                                inclusive = event.inclusive
                            }
                        }
                        launchSingleTop = event.singleTop
                    }
                }
                is NavigationEvent.PopBackStack -> {
                    navController.popBackStack()
                }
                is NavigationEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

    val esPantallaDetalle = rutaActual?.startsWith("detail_page/") == true
    val tituloTopBar = when {
        rutaActual == Screen.Home.route -> "Inicio - AppProyecto_GrupoX"
        rutaActual == Screen.Profile.route -> "Perfil de Usuario"
        rutaActual == Screen.Settings.route -> "Configuración"
        esPantallaDetalle -> "Detalle del Elemento"
        else -> "AppProyecto_GrupoX"
    }

    // Menú lateral deslizable ModalNavigationDrawer (Sección 4 - Guía 8)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerSheetContent(
                rutaActual = rutaActual,
                drawerState = drawerState,
                scope = scope,
                onNavigate = { screen ->
                    viewModel.navigateTo(screen)
                }
            )
        }
    ) {
        // Contenedor base Scaffold con topBar, bottomBar e innerPadding (Sección 3.D y 4)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppTopBar(
                    titulo = tituloTopBar,
                    mostrarBotonAtras = esPantallaDetalle,
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    onBackClick = {
                        viewModel.navigateUp()
                    }
                )
            },
            bottomBar = {
                AppBottomNavigationBar(
                    rutaActual = rutaActual,
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                    }
                )
            }
        ) { innerPadding ->
            // Grafo de navegación NavHost con startDestination = Screen.Home.route (Sección 3.D)
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screen.Home.route) {
                    HomeScreenAdaptable(viewModel = viewModel)
                }

                composable(route = Screen.Profile.route) {
                    ProfileScreen(viewModel = viewModel)
                }

                composable(route = Screen.Settings.route) {
                    SettingsScreen(viewModel = viewModel)
                }

                composable(
                    route = Screen.Detail.ROUTE_PATTERN,
                    arguments = listOf(
                        navArgument(Screen.Detail.ARG_ITEM_ID) {
                            type = NavType.StringType
                            defaultValue = "guia_07"
                        }
                    )
                ) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString(Screen.Detail.ARG_ITEM_ID) ?: "guia_07"
                    DetailScreen(
                        itemId = itemId,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
