package com.zm.org.balance.data.model

data class Transaction(
    val title: String,
    val Type: TransactionType,
    // all amounts expected will be in same Currency,
    // so for now following kiss principle will keep it simple without currency support
    val amount: Float,
    val creationDate: Long, // DateTime in timestamp format
)
