package com.mary.bankolandia.service

import com.mary.bankolandia.dto.PersonDto
import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.dto.UpdatePersonDto
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.model.User
import java.util.UUID

interface PersonService {

    fun createPerson(registerRequest: RegisterRequest,user: User)
    fun getPerson(id: UUID): PersonDto
    fun getPersons(firstName: String?, lastName: String?, email: String?, nif: String?, phoneNumber: String?, statusEnum: StatusEnum?):List<PersonDto>
    fun updatePerson(id:UUID,personDto: UpdatePersonDto)
}