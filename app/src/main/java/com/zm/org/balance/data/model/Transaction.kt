package com.zm.org.balance.data.model

import com.zm.org.balance.util.TimeMillis


data class Transaction(
    val title: String,
    val type: TransactionType,
    // all amounts expected will be in same Currency,
    // so for now following kiss principle will keep it simple without currency support
    val amount: Float,
    val creationDateMillis: TimeMillis, // DateTime in timestamp format
)
