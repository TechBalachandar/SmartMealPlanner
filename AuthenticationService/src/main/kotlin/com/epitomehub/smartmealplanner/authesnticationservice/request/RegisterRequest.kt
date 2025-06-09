package com.epitomehub.smartmealplanner.authesnticationservice.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
