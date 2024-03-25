package br.com.auth.controller

import br.com.auth.entities.Jwt
import br.com.auth.entities.SignIn
import br.com.auth.entities.SignUp
import br.com.auth.entities.User
import br.com.auth.jwt.TokenProvider
import br.com.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var service: AuthService

    @Autowired
    private lateinit var tokenService: TokenProvider

    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid data: SignUp): ResponseEntity<*> {
        service.signUp(data)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    @PostMapping("/signIn")
    fun signIn(@RequestBody @Valid data: SignIn): ResponseEntity<Jwt> {
        val usernamePassword = UsernamePasswordAuthenticationToken(data.login, data.password)
        val authUser: Authentication = authenticationManager.authenticate(usernamePassword)
        val accessToken: String = tokenService.generateAccessToken(authUser.principal as User)
        return ResponseEntity.ok(Jwt(accessToken))
    }
}
