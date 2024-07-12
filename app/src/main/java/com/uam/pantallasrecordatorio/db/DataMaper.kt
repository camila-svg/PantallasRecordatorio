package com.uam.pantallasrecordatorio.db

import com.uam.pantallasrecordatorio.entity.Recordatorio as Ucaredb
import com.uam.pantallasrecordatorio.model.RecordatorioItem as RecordatorioDomain

fun Ucaredb.toRecordatorioDomain() : RecordatorioDomain = RecordatorioDomain(
    nombreActividad,id,fechaHora,realizado
)
fun RecordatorioDomain.toUcaredb() : Ucaredb = Ucaredb(
    id,fechaHora,nombreActividad,realizado
)