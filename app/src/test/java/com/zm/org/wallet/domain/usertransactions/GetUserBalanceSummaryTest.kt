package com.zm.org.wallet.domain.usertransactions

import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.data.usertransactions.UserTransactionsRepository
import com.zm.org.wallet.domain.entity.UserBalanceSummary
import com.zm.org.wallet.util.TimeMillis
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserBalanceSummaryTest {

    private val mockedUserTransactionsRepository = mockk<UserTransactionsRepository>()
    private val sysUnderTest = GetUserBalanceSummaryUseCase(
        userTransactionsRepository = mockedUserTransactionsRepository
    )

    @Test
    fun `when invoke user balance summery then get expected balance`() =
        runTest {
            // Arrange
            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.INCOME
                )
            } returns listOf(
                TransactionEntity("INCOME #1", TransactionType.INCOME, 100f, TimeMillis()),
                TransactionEntity("INCOME #2", TransactionType.INCOME, 50f, TimeMillis())
            )

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(
                TransactionEntity("EXPENSE #1", TransactionType.EXPENSE, 50f, TimeMillis()),
                TransactionEntity("EXPENSE #2", TransactionType.EXPENSE, 20f, TimeMillis())
            )

            // Act
            val result = sysUnderTest.invoke()
            // Assert
            val expectedResult = UserBalanceSummary(
                70f, 150f, 80f
            )
            assertEquals(expectedResult, result)
        }

    @Test
    fun `when invoke user balance summery  with more expenses then get expected negative balance`() =
        runTest {
            // Arrange
            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.INCOME
                )
            } returns listOf(TransactionEntity("INCOME #1", TransactionType.INCOME, 10f, TimeMillis()))

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(TransactionEntity("EXPENSE #1", TransactionType.EXPENSE, 100f, TimeMillis()))

            // Act
            val result = sysUnderTest.invoke()
            // Assert
            val expectedResult = UserBalanceSummary(
                100f, 10f, -90f
            )
            assertEquals(expectedResult, result)
        }

    @Test
    fun `when invoke user balance summery  with more incomes then get expected positive balance`() =
        runTest {
            // Arrange
            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.INCOME
                )
            } returns listOf(TransactionEntity("INCOME #1", TransactionType.INCOME, 100f, TimeMillis()))

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(TransactionEntity("EXPENSE #1", TransactionType.EXPENSE, 10f, TimeMillis()))

            // Act
            val result = sysUnderTest.invoke()
            // Assert
            val expectedResult = UserBalanceSummary(
                10f, 100f, 90f
            )
            assertEquals(expectedResult, result)
        }
}
