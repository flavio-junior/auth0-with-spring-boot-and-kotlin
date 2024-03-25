package br.com.auth.service

import br.com.auth.entities.SignUp
import br.com.auth.entities.User
import br.com.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService : UserDetailsService {

    @Autowired
    private lateinit var repository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserDetails? = repository.findByLogin(username)
        return user!!
    }

    fun signUp(data: SignUp): UserDetails {
        val authentication: UserDetails? = repository.findByLogin(data.login)
        if (authentication != null) {
            throw RuntimeException("Username already exists")
        } else {
            val newUser = User()
            newUser.login = data.login
            newUser.password = BCryptPasswordEncoder().encode(data.password)
            newUser.role = data.role
            return repository.save(newUser)
        }
    }
}