package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.ItemModel
import com.example.app.model.UserProfile
import com.example.app.navigation.NavigationEvent
import com.example.app.navigation.Screen
import com.example.app.repository.SampleRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel principal (Sección 3.C - Guía 10).
 * Gestiona tanto el estado de presentación como la emisión desacoplada de eventos de navegación.
 */
class MainViewModel(
    private val repository: SampleRepository = SampleRepository()
) : ViewModel() {

    // Flujo de emisión privado y público para eventos de navegación (Sección 3.C)
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    // Estados de datos reactivos para las pantallas
    private val _items = MutableStateFlow<List<ItemModel>>(emptyList())
    val items: StateFlow<List<ItemModel>> = _items.asStateFlow()

    private val _userProfile = MutableStateFlow(repository.obtenerPerfilUsuario())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _notificacionesActivas = MutableStateFlow(true)
    val notificacionesActivas: StateFlow<Boolean> = _notificacionesActivas.asStateFlow()

    private val _modoCompactoForzado = MutableStateFlow(false)
    val modoCompactoForzado: StateFlow<Boolean> = _modoCompactoForzado.asStateFlow()

    init {
        cargarDatosIniciales()
    }

    private fun cargarDatosIniciales() {
        _items.value = repository.obtenerItems()
    }

    fun obtenerItemPorId(itemId: String): ItemModel {
        return repository.obtenerItemPorId(itemId)
    }

    fun alternarNotificaciones(activo: Boolean) {
        _notificacionesActivas.value = activo
    }

    fun alternarModoCompacto(activo: Boolean) {
        _modoCompactoForzado.value = activo
    }

    // Funciones ejecutoras de navegación dentro de viewModelScope (Sección 3.C)
    fun navigateTo(
        screen: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = true
    ) {
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = screen,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    singleTop = singleTop
                )
            )
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}
