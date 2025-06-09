package com.epitomehub.smartmealplanner.authesnticationservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

enum class Role {
    USER,
    ADMIN,
}

@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    val email: String,
    val password: String,
    val role: Role = Role.USER,
)
