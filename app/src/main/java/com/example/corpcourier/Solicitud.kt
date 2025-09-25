package com.example.corpcourier

data class Solicitud(
    val id: Int = 0,
    val nombre: String,
    val telefono: String,
    val direccion: String,
    val municipio: String,
    val descripcion: String
)
