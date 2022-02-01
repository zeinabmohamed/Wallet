package com.zm.org.wallet.domain.entity

import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.util.TimeMillis

data class Transaction(
    val title: String,
    val type: TransactionType,
    val amount: Float,
    val creationDateMillis: TimeMillis,
)
