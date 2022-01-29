package com.zm.org.balance.domain.entity

data class UserBalanceSummary(
    val totalExpenses: Float,
    val totalIncomes: Float,
    val balance: Float
)
