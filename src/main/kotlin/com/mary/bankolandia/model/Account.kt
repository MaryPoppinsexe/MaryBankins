package com.mary.bankolandia.model

import com.mary.bankolandia.enumerator.StatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "account")
class Account : BaseEntity() {

    @Column(unique = true, nullable = false)
    lateinit var accountNumber: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @Column(nullable = false, precision = 19, scale = 2)
    var balance: BigDecimal = BigDecimal.ZERO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: StatusEnum = StatusEnum.ACTIVE

    @Column(nullable = false)
    var label: String = "Account"
}