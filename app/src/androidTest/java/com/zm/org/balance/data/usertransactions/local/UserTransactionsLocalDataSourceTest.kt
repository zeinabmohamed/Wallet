package com.zm.org.balance.data.usertransactions.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zm.org.balance.data.database.AppDatabase
import com.zm.org.balance.data.database.TransactionsDao
import com.zm.org.balance.data.model.TransactionEntity
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.util.TimeMillis
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserTransactionsLocalDataSourceTest {
    private lateinit var transactionsDao: TransactionsDao
    private lateinit var appDatabase: AppDatabase
    private val sysUnderTest: UserTransactionsLocalDataSource by lazy {
        UserTransactionsLocalDataSource(transactionsDao)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        transactionsDao = appDatabase.transactionsDao()
    }

    @Test
    @Throws(Exception::class)
    fun createUserTransaction_insertNewTransactionToDB() = runTest {
        // Arrange
        val transaction = TransactionEntity(
            "INCOME #1", TransactionType.EXPENSE, 100f,
            TimeMillis(111)
        )

        // Act
        sysUnderTest.createUserTransaction(transaction)

        // Assert
        val result = transactionsDao.getAll()
        assertEquals(listOf(TransactionEntity(
            "INCOME #1", TransactionType.EXPENSE, 100f,
            TimeMillis(111)
        )), result)
    }


    @Test
    fun getUserAllTransactions_ReturnAllTransactionsInDB() = runTest {
        // Arrange
        sysUnderTest.createUserTransaction(
            TransactionEntity("INCOME #1", TransactionType.INCOME, 10f, TimeMillis(111))
        )
        sysUnderTest.createUserTransaction(
            TransactionEntity("INCOME #1", TransactionType.EXPENSE, 20f, TimeMillis(222))
        )

        // Act
        val result = transactionsDao.getAll()
        assertEquals(listOf(
            TransactionEntity("INCOME #1", TransactionType.INCOME, 10f, TimeMillis(111)),
            TransactionEntity("INCOME #1", TransactionType.EXPENSE, 20f, TimeMillis(222))
        ), result)
    }

    @Test
    fun getUserTransactionsForTransactionType_ReturnOnlyFilteredTypeTransactions() = runTest {
        // Arrange
        sysUnderTest.createUserTransaction(
            TransactionEntity("INCOME #1", TransactionType.INCOME, 10f, TimeMillis(111))
        )
        sysUnderTest.createUserTransaction(
            TransactionEntity("INCOME #1", TransactionType.EXPENSE, 20f, TimeMillis(222))
        )

        // Act
        val result = transactionsDao.getByTransactionType(TransactionType.INCOME)
        assertEquals(listOf(
            TransactionEntity("INCOME #1", TransactionType.INCOME, 10f, TimeMillis(111)),
        ), result)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

}

