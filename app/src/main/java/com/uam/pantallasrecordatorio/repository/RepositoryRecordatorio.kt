package com.uam.pantallasrecordatorio.repository

import android.content.Context
import android.util.Log
import com.uam.pantallasrecordatorio.db.DataBaseApp
import com.uam.pantallasrecordatorio.entity.Recordatorio
import com.uam.pantallasrecordatorio.model.ListRecordatorio
import com.uam.pantallasrecordatorio.model.RecordatorioItem
import com.uam.pantallasrecordatorio.remote.ApiAdapter
import com.uam.pantallasrecordatorio.remote.ApiRecordatorio
import retrofit2.Response


class RepositoryRecordatorio(private val context : Context) {
    val apiRecordatorio : ApiRecordatorio by lazy {
        ApiAdapter.getInstance().create(ApiRecordatorio :: class.java)
    }

    suspend fun getAll() : Result<ListRecordatorio> {
        val retorno : ListRecordatorio
        return try {
            val response : Response<ListRecordatorio> = apiRecordatorio.getAll()
            retorno = response.body() as ListRecordatorio
            Log.d("OK", "${retorno}")
            Result.success(retorno)
        } catch (e : Exception) {
            Log.d("error", "${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateRecordatorio(lista: List<Recordatorio>) {
        val db = DataBaseApp.getDatabase(context)
        db.recordatorioDAO().deleteList(lista)
        db.recordatorioDAO().insertList(lista)
    }

    suspend fun createRecordatorio(recordatorioItem: RecordatorioItem) : Result<RecordatorioItem> {
        val retorno: RecordatorioItem
        return try {
            val response: Response<RecordatorioItem> = apiRecordatorio.create(recordatorioItem)
            retorno = response.body() as RecordatorioItem
            Result.success(retorno)
        } catch (e: Exception) {
            Log.d("error", "${e.message}")
            Result.failure(e)
        }
    }



}