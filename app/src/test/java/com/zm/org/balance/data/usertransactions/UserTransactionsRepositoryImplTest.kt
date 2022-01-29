package com.zm.org.balance.data.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.local.UserTransactionsLocalDataSource
import com.zm.org.balance.util.TimeMillis
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.any
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class UserTransactionsRepositoryImplTest {

    private val mockedUserTransactionsLocalDataSource = mockk<UserTransactionsLocalDataSource>()
    private val sysUnderTest =
        UserTransactionsRepositoryImpl(mockedUserTransactionsLocalDataSource)

    @Before
    fun setup() {
        coEvery { mockedUserTransactionsLocalDataSource.createUserTransaction() } returns true
    }

    @Test
    fun getUserTransactionsForTransactionType() {
    }

    @Test
    fun getUserAllTransactions() {
    }

    @Test
    fun `createUserTransaction should invoke LocalDataSource createUserTransaction()`() = runTest {
        // Act
        sysUnderTest.createUserTransaction(
            Transaction(
                "", TransactionType.EXPENSE, 100f, TimeMillis()
            )
        )
        // Assert
        coVerify(atMost = 1) { mockedUserTransactionsLocalDataSource.createUserTransaction() }
    }

}
