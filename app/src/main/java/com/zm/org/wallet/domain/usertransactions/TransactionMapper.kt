package com.zm.org.wallet.domain.usertransactions

import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.domain.entity.Transaction
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

    fun toTransactionEntity(transaction: Transaction) = TransactionEntity(
        title = transaction.title,
        type = transaction.type,
        amount = transaction.amount,
        creationDateMillis = transaction.creationDateMillis
    )

    fun fromTransactionEntity(transactionEntity: TransactionEntity) = Transaction(
        title = transactionEntity.title,
        type = transactionEntity.type,
        amount = transactionEntity.amount,
        creationDateMillis = transactionEntity.creationDateMillis
    )
}
