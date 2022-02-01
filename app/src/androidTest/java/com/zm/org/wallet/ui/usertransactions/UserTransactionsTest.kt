package com.zm.org.wallet.ui.usertransactions

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.zm.org.wallet.data.database.AppDatabase
import com.zm.org.wallet.data.database.TransactionsDao
import com.zm.org.wallet.data.model.TransactionType
import com.zm.org.wallet.ui.ContainerActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject


@HiltAndroidTest
class UserTransactionsTest {
    @get:Rule
    var composeTestRule = createAndroidComposeRule<ContainerActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var transactionsDao: TransactionsDao

    @Before
    fun createDb() {
        hiltRule.inject()
    }

    @Test
    fun assert_userWithNoTransactionsHistory_shouldSeeEmptyView() {
        // Given
        iamOnTransactionsHistoryScreen()
        // Assert
        validateSeeEmptyHomeTransactionsImage()
    }


    @Test
    fun assert_user_shouldAbleToAddTransaction() {
        // Given
        iamOnTransactionsHistoryScreen()

        // Action
        addNewTransaction("Transaction #1", 1000f, TransactionType.INCOME)
        addNewTransaction("Transaction #2", 20f, TransactionType.EXPENSE)
        // Assert
        composeTestRule.waitForIdle()
        iSeeBalanceSummeryUpdated("- 20.00 $", "1000.00 $", "980.00 $")
        composeTestRule.waitForIdle()
        iSeeMyTransactionHistoryList(childrenCount = 2)

    }
    // region test steps
    private fun iamOnTransactionsHistoryScreen() {
        composeTestRule.onNodeWithTag("screenTitle").assertTextEquals("Transactions History")
    }

    private fun validateSeeEmptyHomeTransactionsImage() {
        composeTestRule.onNodeWithTag("EmptyViewContainer").assertExists()
        composeTestRule.onNodeWithTag("EmptyViewImage").assertExists()
    }

    private fun addNewTransaction(title: String, amount: Float, type: TransactionType) {
        composeTestRule.onNodeWithTag("addTransactionButton").performClick()
        composeTestRule.onNodeWithText("Add Transaction").assertExists()
        composeTestRule.onNodeWithTag("transactionType").performClick()
        composeTestRule.onNodeWithTag("itemLabel:$type").performClick()
        composeTestRule.onNodeWithTag("transactionDesc").performTextInput(title)
        composeTestRule.onNodeWithTag("transactionAmount").performTextInput(amount.toString())

        composeTestRule.onNodeWithTag("AddButton").performClick()
    }

    private fun iSeeBalanceSummeryUpdated(expenses: String, incomes: String, balance: String) {
        composeTestRule.onNodeWithTag("expensesAmountLabel").assertTextEquals(expenses)
        composeTestRule.onNodeWithTag("incomesAmountLabel").assertTextEquals(incomes)
        composeTestRule.onNodeWithTag("balanceAmountLabel").assertTextEquals(balance)
    }

    private fun iSeeMyTransactionHistoryList(childrenCount: Int) {
        composeTestRule.onAllNodesWithTag("transactionHistoryListRow")
            .assertCountEquals(childrenCount)
    }

    //endregion

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.clearAllTables()
    }

}
