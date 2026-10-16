package com.mary.bankolandia.controller

import com.mary.bankolandia.dto.PersonDto
import com.mary.bankolandia.dto.UpdatePersonDto
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.service.PersonService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService,) {

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id: UUID): ResponseEntity<PersonDto> {
        return ResponseEntity.ok(personService.getPerson(id))

    }
    //TODO query params
    @GetMapping
    fun getPersons(@RequestParam(required = false) firstName: String?,
                   @RequestParam(required = false) lastName: String?,
                   @RequestParam(required = false) email: String?,
                   @RequestParam(required = false) nif: String?,
                   @RequestParam(required = false) phoneNumber: String?,
                   @RequestParam(required = false) statusEnum: StatusEnum?,
    ): ResponseEntity<List<PersonDto>> {
        return ResponseEntity.ok(
            personService.getPersons(
            firstName = firstName,
            lastName = lastName,
            email = email,
            nif = nif,
            phoneNumber = phoneNumber,
                statusEnum = statusEnum
        )
        )
    }


    @PatchMapping("/{id}")
    fun updatePerson(@PathVariable id: UUID, @RequestBody @Valid personDto: UpdatePersonDto): ResponseEntity<Void> {
        personService.updatePerson(id, personDto)
        return ResponseEntity.noContent().build()
    }

}