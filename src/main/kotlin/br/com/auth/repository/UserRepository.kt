package br.com.auth.repository

import br.com.auth.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByLogin(login: String?): UserDetails?
}