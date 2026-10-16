package com.mary.bankolandia.enumerator

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER_DEBIT,  // money leaving an account in a transfer
    TRANSFER_CREDIT, // money arriving in an account in a transfer
}

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REVERSED,
}
