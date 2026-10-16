package com.mary.bankolandia.service.impl

import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.dto.UserUpdateRequest
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.model.User
import com.mary.bankolandia.repository.UserRepository
import com.mary.bankolandia.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder): UserService {

    override fun createUser(registerRequest: RegisterRequest):User {
       return userRepository.save(registerRequest.toUser())
    }

    override fun updateUser(id: UUID, userUpdateRequest: UserUpdateRequest) {
        userRepository.findById(id).ifPresent {
            userRepository.save(userUpdateRequest.updateUser(it))
        }
    }

    private fun UserUpdateRequest.updateUser(user: User) =
        user.apply {
            statusEnum = this@updateUser.statusEnum ?: statusEnum
            roleEnum = this@updateUser.roleEnum ?: roleEnum
            password = this@updateUser.password?.let { passwordEncoder.encode(it) } ?: user.password
        }

    private fun RegisterRequest.toUser() =
        User().apply {
            username = this@toUser.username
            statusEnum = StatusEnum.ACTIVE
            password = passwordEncoder.encode(this@toUser.password).toString()
            roleEnum = this@toUser.roleEnum
        }
}