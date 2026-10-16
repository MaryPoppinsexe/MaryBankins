package com.mary.bankolandia.service

import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.dto.UserUpdateRequest
import com.mary.bankolandia.model.User
import java.util.UUID

interface UserService {

    fun createUser(registerRequest: RegisterRequest): User
    fun updateUser(id: UUID, userUpdateRequest: UserUpdateRequest)

}