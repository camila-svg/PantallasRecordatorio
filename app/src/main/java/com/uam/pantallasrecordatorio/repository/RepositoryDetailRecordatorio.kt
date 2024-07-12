package com.uam.pantallasrecordatorio.repository

import android.content.Context
import android.util.Log
import com.uam.pantallasrecordatorio.db.DataBaseApp
import com.uam.pantallasrecordatorio.entity.Recordatorio


class RepositoryDetailRecordatorio(private val  context: Context) {

        val db = DataBaseApp.getDatabase(context)


        suspend fun getDetail(id : String) : Recordatorio {
            val recordatorio = db.recordatorioDAO().findById(id)
            Log.d("REPOSITORY","RECORDATORIO $recordatorio")
            return recordatorio
        }

        suspend fun saveDetail(recordatorio: Recordatorio) {
            db.recordatorioDAO().insertObj(recordatorio)
        }

}