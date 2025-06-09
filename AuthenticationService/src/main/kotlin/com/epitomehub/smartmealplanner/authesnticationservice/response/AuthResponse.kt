package com.epitomehub.smartmealplanner.authesnticationservice.response

data class AuthResponse(
    val token: String,
    val statusCode: Int,
    val errorMessage: String?,
)