package com.zm.org.balance.domain.usertransactions

import com.zm.org.balance.data.model.Transaction
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.util.TimeMillis
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

    // Note : 1643400000000 == Jan 29 2022
    @Test
    fun `when transactions created in same day Jan 29 2022 then should be in same day group in result map`() =
        runTest {
            // Arrange
            coEvery { mockedUserTransactionsRepository.getUserAllTransactions() } returns
                    given29JanTransactions

            // Act
            val result = sysUnderTest.invoke()

            val expectedResult = mapOf(
                Pair(TimeMillis(1643400000000), given29JanTransactions),
            )
            // Assert
            assertEquals(expectedResult, result)
        }


    @Test
    fun `when we have transaction in different days then get expected map of Transactions grouped by date`() =
        runTest {
            // Arrange
            coEvery { mockedUserTransactionsRepository.getUserAllTransactions() } returns
                    given29JanTransactions + given28JanTransactions

            // Act
            val result = sysUnderTest.invoke()

            val expectedResult = mapOf(
                Pair(TimeMillis(1643400000000), given29JanTransactions),
                Pair(TimeMillis(1643313600000), given28JanTransactions)
            )
            // Assert
            assertEquals(expectedResult, result)
        }

    //region utils

    private val given29JanTransactions = listOf(
        Transaction(
            "INCOME #1",
            TransactionType.EXPENSE,
            100f,
            TimeMillis(1643439600000)
        ) /* Sat Jan 29 2022 11:00:00 */,
        Transaction(
            "INCOME #2",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643464800000)
        ) /* Sat Jan 29 2022 18:00:00 */,
        Transaction(
            "INCOME #3",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643486399000)
        ) /* Sat Jan 29 2022 23:59:59 */,
        Transaction(
            "INCOME #4",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643486340000)
        ) /* Sat Jan 29 2022 19:59:00 */
    )
    private val given28JanTransactions = listOf(
        Transaction(
            "INCOME #1",
            TransactionType.EXPENSE,
            100f,
            TimeMillis(1643353200000)
        ) /* Fri Jan 28 2022 11:00:00 */,
        Transaction(
            "INCOME #2",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643378400000)
        ) /* Fri Jan 28 2022 18:00:00 */,
        Transaction(
            "INCOME #3",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643399999000)
        ) /* Fri Jan 28 2022 23:59:59 */,
        Transaction(
            "INCOME #4",
            TransactionType.INCOME,
            50f,
            TimeMillis(1643399940000)
        ) /* Fri Jan 28 2022 23:59:00 */
    )
    //endregion
}
