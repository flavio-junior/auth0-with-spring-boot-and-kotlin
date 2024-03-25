package br.com.auth.jwt

import br.com.auth.entities.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenProvider {

    @Value("\${security.jwt.token.secret-key}")
    private val jwtSecret: String? = null

    fun generateAccessToken(user: User): String {
        return try {
            val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)
            JWT.create()
                .withSubject(user.username)
                .withClaim("username", user.username)
                .withExpiresAt(genAccessExpirationDate())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw JWTCreationException("Error while generating token", exception)
        }
    }

    fun validateToken(token: String?): String {
        return try {
            val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)
            JWT.require(algorithm)
                .build()
                .verify(token)
                .subject
        } catch (exception: JWTVerificationException) {
            throw JWTVerificationException("Error while validating token", exception)
        }
    }

    private fun genAccessExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }
}
