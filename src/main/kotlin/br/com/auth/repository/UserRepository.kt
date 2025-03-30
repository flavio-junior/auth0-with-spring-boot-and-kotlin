package br.com.auth.repository

import br.com.auth.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User?, Long?> {

    @Query(value = "SELECT u FROM User u WHERE u.email =:email")
    fun fetchByEmail(
        @Param("email") email: String?
    ): User?

    fun findByEmail(
        email: String?
    ): UserDetails?
}