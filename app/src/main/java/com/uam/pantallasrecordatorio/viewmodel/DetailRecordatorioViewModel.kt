package com.uam.pantallasrecordatorio.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uam.pantallasrecordatorio.db.toUcaredb
import com.uam.pantallasrecordatorio.model.ListRecordatorio
import com.uam.pantallasrecordatorio.repository.RepositoryRecordatorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgendaViewModel(context: Context) : ViewModel() {

    private val repository = RepositoryRecordatorio(context)

    private val _state = MutableStateFlow<UIState>(UIState())
    val state: StateFlow<UIState> = _state

    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response = repository.getAll()
            if (response.isSuccess) {
                _state.update{it.copy(listRecordatorio = response.getOrNull()!!)}
                repository.updateRecordatorio(response.getOrNull()!!.map { it.toUcaredb()})
            }
            _state.update{it.copy(loading = false)}
        }
    }

    data class UIState(
        val listRecordatorio: ListRecordatorio = ListRecordatorio(),
        val loading: Boolean = false
    )

}

class DetailRecordatorioViewModelFactory(private val context: Context, idRecordatorio: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaViewModel(context) as T
    }
}