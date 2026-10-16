package com.mary.bankolandia.dto

import com.mary.bankolandia.enumerator.RoleEnum
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.validator.ValidRole
import com.mary.bankolandia.validator.ValidStatus

class UserUpdateRequest (
    @field:ValidRole
    val roleEnum: RoleEnum? = null,
    @field:ValidStatus
    val statusEnum: StatusEnum? = null,
    val password: String? = null
)
