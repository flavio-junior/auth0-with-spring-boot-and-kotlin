package br.com.auth.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table
@Entity(name = "tb_user")
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var login: String? = null

    private var password: String? = null

    @Enumerated(EnumType.STRING)
    var role: UserRole? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        if (this.role == UserRole.ADMIN) {
            return arrayListOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        }
        return arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return login!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}