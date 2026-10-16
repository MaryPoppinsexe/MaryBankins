package com.mary.bankolandia.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration-ms}") private val accessTokenExpirationMs: Long,
    @Value("\${jwt.refresh-token-expiration-ms}") private val refreshTokenExpirationMs: Long,
) {
    private val signingKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateAccessToken(userDetails: UserDetails): String =
        buildToken(userDetails.username, accessTokenExpirationMs)

    fun generateRefreshToken(userDetails: UserDetails): String =
        buildToken(userDetails.username, refreshTokenExpirationMs)

    fun refreshTokenExpirationMs(): Long = refreshTokenExpirationMs

    fun extractUsername(token: String): String? =
        runCatching { extractAllClaims(token).subject }.getOrNull()

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token) ?: return false
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = runCatching { extractAllClaims(token).expiration }.getOrNull()
            ?: return true
        return expiration.before(Date())
    }

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload

    private fun buildToken(subject: String, expirationMs: Long): String =
        Jwts.builder()
            .subject(subject)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(signingKey)
            .compact()
}