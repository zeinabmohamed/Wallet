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
import java.lang.AssertionError
import java.util.*

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
    fun `when add Transaction with empty Title should return result False`() {
        // Act , Assert
        val exceptionFailure = org.junit.Assert.assertThrows(
            java.lang.AssertionError::class.java
        ) {
            runTest {
                sysUnderTest.invoke(
                    Transaction(
                        title = "",
                        type = TransactionType.EXPENSE,
                        amount = 44f,
                        creationDateMillis = TimeMillis()
                    )
                )
            }
        }
        assertEquals(
            "Invalid transaction title >> ",
            exceptionFailure.message
        )
    }

    @Test
    fun `when add Transaction with 0 amount should return result False`() {
        // Act , Assert
        val exceptionFailure = org.junit.Assert.assertThrows(
            java.lang.AssertionError::class.java
        ) {
            runTest {
                sysUnderTest.invoke(
                    Transaction(
                        title = "EXPENSE #1",
                        type = TransactionType.EXPENSE,
                        amount = 0f,
                        creationDateMillis = TimeMillis()
                    )
                )
            }
        }
        assertEquals(
            "Invalid transaction Amount >> 0.0",
            exceptionFailure.message
        )
    }


    @Test
    fun `when add Transaction with -ve amount should return result False`() {
        // Act , Assert
        val exceptionFailure = assertThrows(
            AssertionError::class.java
        ) {
            runTest {
                sysUnderTest.invoke(
                    Transaction(
                        title = "EXPENSE #1",
                        type = TransactionType.EXPENSE,
                        amount = -100f,
                        creationDateMillis = TimeMillis()
                    )
                )
            }
        }
        assertEquals("Invalid transaction Amount >> -100.0", exceptionFailure.message)
    }


    @Test
    fun `when add Transaction with future date should return result False`() {
        // Act , Assert
        val exceptionFailure = assertThrows(
            AssertionError::class.java
        ) {
            runTest {
                sysUnderTest.invoke(
                    Transaction(
                        title = "EXPENSE #1",
                        type = TransactionType.EXPENSE,
                        amount = 100f,
                        creationDateMillis = TimeMillis(Calendar.getInstance().run {
                            this.timeInMillis = 1675002854010
                            this.set(Calendar.YEAR, 2023)
                            this
                        }.timeInMillis)
                    )
                )
            }
        }
        assertEquals("Invalid transaction creationDate >> 1675002854010", exceptionFailure.message)
    }

}
