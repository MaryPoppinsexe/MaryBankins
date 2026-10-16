package com.mary.bankolandia.model

import com.mary.bankolandia.enumerator.TransactionStatus
import com.mary.bankolandia.enumerator.TransactionType
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
@Table(name = "transaction")
class Transaction : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    lateinit var account: Account

    @Column(nullable = false, precision = 19, scale = 2)
    lateinit var amount: BigDecimal

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var type: TransactionType

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TransactionStatus = TransactionStatus.COMPLETED


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_transaction_id")
    var relatedTransaction: Transaction? = null


    var description: String? = null
}