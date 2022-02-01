package com.zm.org.balance.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoneyFormatter @Inject constructor() {

    private val OneThousand = 1000
    private val OneMillion = 1000000
    fun format(amount: Float): String {
        return when {
            amount > OneMillion -> {
                String.format("%.02f", (amount/(OneMillion))).plus("M")
            }
            amount > OneThousand -> {
                String.format("%.02f", (amount/(OneThousand))).plus("K")
            }
            else -> {
                String.format("%.02f", amount)
            }
        }
    }
}
