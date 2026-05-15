package com.example.nit3213app.model

data class DashboardResponse(
    val entities: List<Map<String, Any?>>,
    val entityTotal: Int
)