package com.zm.org.wallet.data.usertransactions

import com.zm.org.wallet.data.model.TransactionEntity
import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.data.usertransactions.local.UserTransactionsLocalDataSource
import com.zm.org.wallet.util.TimeMillis
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Before

class UserTransactionsRepositoryImplTest {

    private val mockedUserTransactionsLocalDataSource = mockk<UserTransactionsLocalDataSource>()
    private val sysUnderTest =
        UserTransactionsRepositoryImpl(mockedUserTransactionsLocalDataSource)

    @Before
    fun setup() {
        coEvery { mockedUserTransactionsLocalDataSource.createUserTransaction(any()) } returns Unit
        coEvery { mockedUserTransactionsLocalDataSource.getUserAllTransactions() } returns emptyList()
        coEvery { mockedUserTransactionsLocalDataSource.getUserTransactionsForTransactionType(any()) } returns emptyList()
    }

    @Test
    fun `getUserTransactionsForTransactionType should invoke LocalDataSource getUserTransactionsForTransactionType`() = runTest {
        // Act
        sysUnderTest.getUserTransactionsForTransactionType(TransactionType.EXPENSE)
        // Assert
        coVerify(atMost = 1) {
            mockedUserTransactionsLocalDataSource.getUserTransactionsForTransactionType(
                TransactionType.EXPENSE
            )
        }
    }


    @Test
    fun `getUserAllTransactions should invoke LocalDataSource getUserAllTransactions`() = runTest {
        // Act
        sysUnderTest.getUserAllTransactions()
        // Assert
        coVerify(atMost = 1) { mockedUserTransactionsLocalDataSource.getUserAllTransactions() }
    }

    @Test
    fun `createUserTransaction should invoke LocalDataSource createUserTransaction()`() = runTest {
        // Arrange
        val transactionToAdd = TransactionEntity(
            "", TransactionType.EXPENSE, 100f, TimeMillis()
        )
        // Act
        sysUnderTest.createUserTransaction(
            transactionToAdd
        )
        // Assert
        coVerify(atMost = 1) {
            mockedUserTransactionsLocalDataSource.createUserTransaction(
                transactionToAdd
            )
        }
    }

}
