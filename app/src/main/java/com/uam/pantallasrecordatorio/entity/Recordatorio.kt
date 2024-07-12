package com.uam.pantallasrecordatorio.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recordatorio (
    @PrimaryKey
    val id: Int,
    val nombreActividad: String,
    val fechaHora: String,
    val realizado: Boolean)
{
    constructor() : this(0,"","",false)
}


