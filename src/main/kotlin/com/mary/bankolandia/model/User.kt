package com.mary.bankolandia.model

import com.mary.bankolandia.enumerator.RoleEnum
import com.mary.bankolandia.enumerator.StatusEnum
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails



@Entity
@Table(name = "app_user")
class User(): BaseEntity(), UserDetails{
    @Column(unique = true)
    lateinit var username: String
    lateinit var password: String
    @Enumerated(EnumType.STRING)
    lateinit var statusEnum: StatusEnum

    @Enumerated(EnumType.STRING)
    lateinit var roleEnum: RoleEnum


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_${roleEnum.name}"))

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = statusEnum == StatusEnum.ACTIVE
}
