package com.example.nit3213app.repository

import com.example.nit3213app.model.DashboardResponse
import com.example.nit3213app.model.LoginRequest
import com.example.nit3213app.model.LoginResponse
import com.example.nit3213app.network.ApiService
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(username, password))
    }

    suspend fun getDashboard(keypass: String): DashboardResponse {
        return apiService.getDashboard(keypass)
    }
}