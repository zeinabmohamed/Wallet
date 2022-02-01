package com.zm.org.wallet.domain.entity

data class UserBalanceSummary(
    val totalExpenses: Float,
    val totalIncomes: Float,
    val balance: Float
)
