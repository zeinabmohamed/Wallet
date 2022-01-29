package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.domain.entity.UserBalanceSummary
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
                Transaction("INCOME #1", TransactionType.INCOME, 100f, 1234),
                Transaction("INCOME #2", TransactionType.INCOME, 50f, 1234)
            )

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(
                Transaction("EXPENSE #1", TransactionType.EXPENSE, 50f, 4321),
                Transaction("EXPENSE #2", TransactionType.EXPENSE, 20f, 4321)
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
            } returns listOf(Transaction("INCOME #1", TransactionType.INCOME, 10f, 1234))

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(Transaction("EXPENSE #1", TransactionType.EXPENSE, 100f, 4321))

            // Act
            val result = sysUnderTest.invoke()
            // Assert
            val expectedResult = UserBalanceSummary(
                50f, 100f, 50f
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
            } returns listOf(Transaction("INCOME #1", TransactionType.INCOME, 100f, 1234))

            coEvery {
                mockedUserTransactionsRepository.getUserTransactionsForTransactionType(
                    TransactionType.EXPENSE
                )
            } returns listOf(Transaction("EXPENSE #1", TransactionType.EXPENSE, 10f, 4321))

            // Act
            val result = sysUnderTest.invoke()
            // Assert
            val expectedResult = UserBalanceSummary(
                10f, 100f, 90f
            )
            assertEquals(expectedResult, result)
        }
}
