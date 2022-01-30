package com.zm.org.balance.domain.entity

import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.util.TimeMillis

data class Transaction(
    val title: String,
    val type: TransactionType,
    val amount: Float,
    val creationDateMillis: TimeMillis,
)
