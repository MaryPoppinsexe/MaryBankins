package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.UserUpdateRequest
import com.mary.bankolandia.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
class UserController(private val userService: UserService) {

    @PatchMapping("/{id}")
    fun updateUser(@PathVariable id: UUID,@RequestBody userUpdateRequest: UserUpdateRequest): ResponseEntity<Void> {
        userService.updateUser(id,userUpdateRequest)
        return ResponseEntity.noContent().build()
    }

}