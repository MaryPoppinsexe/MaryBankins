package com.mary.bankolandia.repository

import com.mary.bankolandia.enumerator.StatusEnum
import com.mary.bankolandia.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PersonRepository : JpaRepository<Person, UUID> {

    @Query(
        """
        SELECT p FROM Person p
        JOIN p.User u
        WHERE (:firstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
          AND (:lastName  IS NULL OR LOWER(p.lastName)  LIKE LOWER(CONCAT('%', :lastName,  '%')))
          AND (:email     IS NULL OR LOWER(p.email)     = LOWER(:email))
          AND (:nif       IS NULL OR p.nif              = :nif)
          AND (:phoneNumber IS NULL OR p.phoneNumber    = :phoneNumber)
          AND (:status      IS NULL OR u.statusEnum             = :status)
    """
    )
    fun findWithFilters(
        @Param("firstName") firstName: String?,
        @Param("lastName") lastName: String?,
        @Param("email") email: String?,
        @Param("nif") nif: String?,
        @Param("phoneNumber") phoneNumber: String?,
        @Param("status") statusEnum: StatusEnum?,
    ): List<Person>

}