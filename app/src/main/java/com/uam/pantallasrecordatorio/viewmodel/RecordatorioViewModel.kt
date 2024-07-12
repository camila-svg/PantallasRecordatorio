package com.uam.pantallasrecordatorio.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uam.pantallasrecordatorio.db.toRecordatorioDomain
import com.uam.pantallasrecordatorio.entity.Recordatorio
import com.uam.pantallasrecordatorio.repository.RepositoryDetailRecordatorio
import com.uam.pantallasrecordatorio.repository.RepositoryRecordatorio
import kotlinx.coroutines.launch

class DetailRecordatorioViewModel(context: Context, idRecordatorio: String) : ViewModel(){

    val repository = RepositoryDetailRecordatorio(context)

    val repositoryRecordatorio = RepositoryRecordatorio(context)

    var recordatorioMutable by mutableStateOf(Recordatorio())
        private set

    var mstate by mutableStateOf(State())
        private set

    fun onNombreActividad(nombreActividad: String){
       recordatorioMutable = recordatorioMutable.copy(nombreActividad = nombreActividad)
    }

    fun onFechaHora(fechahora : String) {
        recordatorioMutable = recordatorioMutable.copy(fechaHora = fechahora)
    }

    fun onId(id: Int) {
        recordatorioMutable = recordatorioMutable.copy(id = id)
    }

    fun onRealizado(realizado: Boolean) {
        recordatorioMutable = recordatorioMutable.copy(realizado = realizado)
    }

    fun onSave(idRecordatorio: String) {
        if (idRecordatorio.equals("-1")) {
            viewModelScope.launch {
                //Save Api
                val response = repositoryRecordatorio.createRecordatorio(recordatorioMutable.toRecordatorioDomain())
                if (response.isSuccess) {
                    mstate = mstate.copy(error = false, mensaje = "Insercion con exito")
                }
                else {
                    mstate = mstate.copy(error = true
                        , mensaje = "Error al insertar ${response.exceptionOrNull()?.message}")
                }
                //Save db
                //repository.saveDetail(response.getOrNull()!!.toAgendaDb())
            }

        }
    }

    fun resetDato() {
        mstate = mstate.copy(error = false
            , mensaje = null)
    }


    init {
        viewModelScope.launch {
            Log.d("VIEWMODEL","IDRecordatorio $idRecordatorio")
            if (idRecordatorio.equals("-1")) {
                recordatorioMutable = Recordatorio()
            }
            else {
                recordatorioMutable = repository.getDetail(idRecordatorio)
            }
            Log.d("VIEWMODEL","RECORDATORIO $recordatorioMutable")
        }
    }

    data class State(
        val mensaje : String? = null,
        val error : Boolean = false
    )



}

class DetailAgendaViewModelFactory(private val context: Context, private val idRecordatorio: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailRecordatorioViewModel(context,idRecordatorio) as T
    }
}