package com.uam.pantallasrecordatorio.db

import androidx.room.Dao
import androidx.room.Query
import com.uam.pantallasrecordatorio.entity.Recordatorio

@Dao
abstract class RecordatorioDAO:BaseDao<Recordatorio> {
    @Query("SELECT * FROM Recordatorio")
    suspend abstract fun getAll() : List<Recordatorio>

    @Query("SELECT * FROM Recordatorio WHERE id = :pid")
    suspend abstract fun findById(pid : String) : Recordatorio

}