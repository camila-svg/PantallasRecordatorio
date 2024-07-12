package com.uam.pantallasrecordatorio.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uam.pantallasrecordatorio.entity.Recordatorio

@Database(
    entities = [Recordatorio::class],
    version = 1
)
abstract class DataBaseApp : RoomDatabase() {
    abstract fun recordatorioDAO():RecordatorioDAO


    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null

        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DataBaseApp::class.java,
                    "Ucaredb")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}