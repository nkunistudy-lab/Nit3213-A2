package com.example.nit3213app.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.model.Entity
import com.example.nit3213app.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _entitiesState = MutableStateFlow<List<Entity>>(emptyList())
    val entitiesState: StateFlow<List<Entity>> = _entitiesState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getDashboard(keypass: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                val entities = response.entities.map { map ->
                    Entity(HashMap(map.mapValues { it.value?.toString() ?: "" }))
                }
                _entitiesState.value = entities
            } catch (e: Exception) {
                _errorState.value = "Error fetching data: ${e.message}"
            }
        }
    }
}