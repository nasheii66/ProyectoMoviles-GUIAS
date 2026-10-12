package com.example.app.repository

import com.example.app.model.ItemModel
import com.example.app.model.UserProfile

/**
 * Repositorio de datos simulado según las convenciones de las Guías (Sección 2).
 * Abstrae el origen de los datos para ser consumidos por el ViewModel.
 */
class SampleRepository {

    fun obtenerItems(): List<ItemModel> {
        return listOf(
            ItemModel(
                id = "guia_07",
                titulo = "Guía 7: Fundamentos UI",
                subtitulo = "Layouts, Imágenes y Material 3",
                descripcion = "Explora el uso de Column, Row, Spacer, Text estilizado con MaterialTheme, Button e Image con painterResource.",
                categoria = "Interfaz"
            ),
            ItemModel(
                id = "guia_08",
                titulo = "Guía 8: Contenedores y Menús",
                subtitulo = "Scaffold, TopAppBar y NavigationDrawer",
                descripcion = "Implementa estructuras completas con barra superior, menú lateral deslizable (ModalNavigationDrawer) y barra inferior (NavigationBar).",
                categoria = "Estructura"
            ),
            ItemModel(
                id = "guia_09",
                titulo = "Guía 9: Diseño Adaptable",
                subtitulo = "WindowSizeClass (Compact, Medium, Expanded)",
                descripcion = "Adapta la interfaz dinámicamente según las dimensiones de la ventana del dispositivo: teléfonos, plegables y tablets.",
                categoria = "Adaptabilidad"
            ),
            ItemModel(
                id = "guia_10",
                titulo = "Guía 10: Navegación MVVM",
                subtitulo = "Navegación Desacoplada con SharedFlow",
                descripcion = "Controla el NavHost mediante eventos reactivos (NavigationEvent) emitidos desde MainViewModel hacia MainActivity.",
                categoria = "Arquitectura"
            )
        )
    }

    fun obtenerItemPorId(itemId: String): ItemModel {
        return obtenerItems().find { it.id == itemId } ?: ItemModel(
            id = itemId,
            titulo = "Elemento #$itemId",
            subtitulo = "Vista de detalle parametrizada",
            descripcion = "Este elemento fue cargado dinámicamente a través de la ruta tipada Screen.Detail(itemId = \"$itemId\").",
            categoria = "General"
        )
    }

    fun obtenerPerfilUsuario(): UserProfile {
        return UserProfile(
            nombre = "Estudiante Desarrollo Móvil",
            rol = "Arquitectura Android & Jetpack Compose",
            correo = "estudiante@universidad.edu",
            grupo = "Grupo X"
        )
    }
}
