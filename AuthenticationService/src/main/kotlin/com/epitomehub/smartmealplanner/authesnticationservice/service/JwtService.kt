package com.epitomehub.smartmealplanner.authesnticationservice.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {

    // Secret key for signing JWTs (you can move it to application.properties securely later)
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)


    fun extractUsername(token: String, secret: String): String {
        return getClaims(token, secret).subject
    }

    fun isTokenValid(token: String, userDetails: UserDetails, secret: String): Boolean {
        val username = extractUsername(token, secret)
        return username == userDetails.username && !isTokenExpired(token, secret)
    }

    private fun getClaims(token: String, secret: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String, secret: String): Boolean {
        return getClaims(token, secret).expiration.before(Date())
    }

    fun generateToken(email: String): String {
        val now = Date()
        val expiry = Date(now.time + 1000 * 60 * 60 * 24) // 24 hours

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey)
            .compact()
    }
}
