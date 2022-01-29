package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.util.TimeMillis
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class AddUserTransactionUseCaseTest {
    private val mockedUserTransactionsRepository = mockk<UserTransactionsRepository>()

    private val sysUnderTest = AddUserTransactionUseCase(
        userTransactionsRepository = mockedUserTransactionsRepository
    )

    @Before
    fun setup() {
        // Arrange
        coEvery { mockedUserTransactionsRepository.createUserTransaction(any()) } returns true
    }

    @Test
    fun `when success to add Transaction should return result True`() =
        runTest {
            // Act
            val result = sysUnderTest.invoke(
                Transaction(
                    title = "EXPENSE #1",
                    type = TransactionType.EXPENSE,
                    amount = 11f,
                    creationDateMillis = TimeMillis()
                )
            )

            // Assert
            assertTrue(result)
        }

    @Test
    fun `when add Transaction with empty Title should return result False`() =
        runTest {

            // Act
            val result = sysUnderTest.invoke(
                Transaction(
                    title = "",
                    type = TransactionType.EXPENSE,
                    amount = 44f,
                    creationDateMillis = TimeMillis()
                )
            )

            // Assert
            assertFalse(result)
        }

    @Test
    fun `when add Transaction with 0 amount should return result False`() =
        runTest {

        }
}
