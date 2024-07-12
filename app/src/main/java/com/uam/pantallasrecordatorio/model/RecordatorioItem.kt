package com.uam.pantallasrecordatorio.model

data class RecordatorioItem(
    val fechaHora: String,
    val id: Int,
    val nombreActividad: String,
    val realizado: Boolean
)