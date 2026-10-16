package com.mary.bankolandia.service.impl

import com.mary.bankolandia.dto.PersonDto
import com.mary.bankolandia.dto.RegisterRequest
import com.mary.bankolandia.dto.UpdatePersonDto
import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.model.Person
import com.mary.bankolandia.model.User
import com.mary.bankolandia.repository.PersonRepository
import com.mary.bankolandia.service.PersonService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class PersonServiceImpl(private val personRepository: PersonRepository) : PersonService {


    override fun createPerson(registerRequest: RegisterRequest,user:User) {
        personRepository.save(registerRequest.toPerson(user))

    }

    override fun getPerson(id: UUID): PersonDto{
       return personRepository.findById(id).map {
           it.toDto()
       }.orElseThrow {
           EntityNotFoundException("Person not found: $id") }
    }


    override fun getPersons(firstName: String?,
                            lastName: String?,
                            email: String?,
                            nif: String?,
                            phoneNumber: String?,
                            statusEnum: StatusEnum?)
    : List<PersonDto> =
        personRepository
            .findWithFilters(firstName, lastName, email, nif, phoneNumber,statusEnum)
            .map { it.toDto() }



    override fun updatePerson(id:UUID,personDto: UpdatePersonDto) {
       personRepository.findById(id).ifPresent {
           personRepository.save(personDto.updatePerson(it))        }

    }
    private fun Person.toDto() =
            PersonDto(
                id = this.id,
                firstName = this.firstName,
                lastName = this.lastName,
                dateOfBirth = this.dateOfBirth,
                nif = this.nif,
                phoneNumber = this.phoneNumber,
                email = this.email
            )
    private fun RegisterRequest.toPerson(user: User) =
        Person().apply {
            firstName = this@toPerson.firstName
            lastName = this@toPerson.lastName
            dateOfBirth = this@toPerson.dateOfBirth
            nif = this@toPerson.nif
            phoneNumber = this@toPerson.phoneNumber
            email = this@toPerson.email
            User = user
        }

    private fun UpdatePersonDto.updatePerson(person: Person) =
        person.apply {
            firstName = this@updatePerson.firstName ?: firstName
            lastName = this@updatePerson.lastName ?: lastName
            email = this@updatePerson.email ?: email
            dateOfBirth = this@updatePerson.dateOfBirth ?: dateOfBirth
            nif = this@updatePerson.nif ?: nif
            phoneNumber = this@updatePerson.phoneNumber ?: phoneNumber
        }


}