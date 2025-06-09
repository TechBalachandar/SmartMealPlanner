package com.epitomehub.smartmealplanner.authesnticationservice.controller

import com.epitomehub.smartmealplanner.authesnticationservice.response.AuthResponse
import com.epitomehub.smartmealplanner.authesnticationservice.request.LoginRequest
import com.epitomehub.smartmealplanner.authesnticationservice.request.RegisterRequest
import com.epitomehub.smartmealplanner.authesnticationservice.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/smartmealplannerapi/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.register(request))

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))
}