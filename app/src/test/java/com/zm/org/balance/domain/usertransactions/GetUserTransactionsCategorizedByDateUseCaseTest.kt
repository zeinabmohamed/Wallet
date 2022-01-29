package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

@ExperimentalCoroutinesApi
class GetUserTransactionsCategorizedByDateUseCaseTest {

    private val mockedUserTransactionsRepository = mockk<UserTransactionsRepository>()

    private val sysUnderTest = GetUserTransactionsCategorizedByDateUseCase(
        mockedUserTransactionsRepository
    )

    @Test
    fun `when no transactions then get empty map`() =
        runTest {
            // Arrange
            coEvery { mockedUserTransactionsRepository.getUserAllTransactions() } returns emptyList()

            // Act
            val result = sysUnderTest.invoke()

            // Assert
            assertEquals(emptyMap<Long, List<Transaction>>(), result)
        }

    @Test
    fun `when transactions created in same day then should be in same day group in result map`() =
        runTest {
        }


    @Test
    fun `when we have transaction in different days then get expected map of Transactions grouped by date`() =
        runTest {
        }
}
