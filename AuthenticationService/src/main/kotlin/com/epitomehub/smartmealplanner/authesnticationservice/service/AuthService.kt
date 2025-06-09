package com.epitomehub.smartmealplanner.authesnticationservice.service

import com.epitomehub.smartmealplanner.authesnticationservice.model.User
import com.epitomehub.smartmealplanner.authesnticationservice.repository.UserRepository
import com.epitomehub.smartmealplanner.authesnticationservice.response.AuthResponse
import com.epitomehub.smartmealplanner.authesnticationservice.request.LoginRequest
import com.epitomehub.smartmealplanner.authesnticationservice.request.RegisterRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.findByEmail(request.email) != null) {
            return AuthResponse(
                token = "",
                statusCode = 409,
                errorMessage = "User already exists"
            )
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.save(user)

        val token = jwtService.generateToken(user.email)
        return AuthResponse(
            token = token,
            statusCode = 200,
            errorMessage = null
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        try {
            val authenticationToken = UsernamePasswordAuthenticationToken(request.email, request.password)
            authenticationManager.authenticate(authenticationToken)
        } catch (ex: Exception) {
            return AuthResponse(
                token = "",
                statusCode = 401,
                errorMessage = "Invalid credentials"
            )
        }

        val user = userRepository.findByEmail(request.email)
            ?: return AuthResponse(
                token = "",
                statusCode = 404,
                errorMessage = "User not found"
            )

        val token = jwtService.generateToken(user.email)
        return AuthResponse(
            token = token,
            statusCode = 200,
            errorMessage = null
        )
    }
}