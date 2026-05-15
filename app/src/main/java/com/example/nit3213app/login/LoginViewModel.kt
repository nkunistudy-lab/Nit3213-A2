package com.example.nit3213app.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorState.value = null
            try {
                val response = repository.login(username, password)
                _loginState.value = response.keypass
            } catch (e: Exception) {
                _errorState.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}