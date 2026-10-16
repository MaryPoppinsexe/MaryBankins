package com.mary.bankolandia.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "person")
class Person: BaseEntity(){
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var dateOfBirth: Date
    @Column(unique = true)
    lateinit var nif:String
    @Column(unique = true)
    lateinit var phoneNumber: String
    @Column(unique = true)
    lateinit var email: String
    @OneToOne
    @JoinColumn(name = "user_id")
    lateinit var User: User
}