package com.uam.pantallasrecordatorio.remote


import com.uam.pantallasrecordatorio.model.ListRecordatorio
import com.uam.pantallasrecordatorio.model.RecordatorioItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRecordatorio {
    @GET("recordatorio/all")
    suspend fun getAll(): Response<ListRecordatorio>

    @POST("recordatorio/create")
    suspend fun create(@Body agenda: RecordatorioItem): Response<RecordatorioItem>
}