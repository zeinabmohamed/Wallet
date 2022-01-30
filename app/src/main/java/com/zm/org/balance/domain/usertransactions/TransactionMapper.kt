package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.TransactionEntity
import com.zm.org.balance.domain.entity.Transaction

class TransactionMapper {

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
