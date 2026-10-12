package com.example.app.model

/**
 * Modelo de datos (Data Class / Entidad de negocio) para un elemento del catálogo
 * o elemento navegable hacia la pantalla de detalle (DetailScreen).
 */
data class ItemModel(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String,
    val categoria: String
)

/**
 * Modelo de datos para la información del perfil del usuario en ProfileScreen.
 */
data class UserProfile(
    val nombre: String,
    val rol: String,
    val correo: String,
    val grupo: String
)
