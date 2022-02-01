package com.zm.org.wallet.ui.usertransactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zm.org.wallet.domain.entity.Transaction
import com.zm.org.wallet.domain.entity.UserBalanceSummary
import com.zm.org.wallet.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.wallet.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.wallet.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.wallet.ui.error.ErrorToUserMessage
import com.zm.org.wallet.ui.usertransactions.TransactionsHistoryViewState.*
import com.zm.org.wallet.util.TimeMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTransactionsViewModel @Inject constructor(
    private val balanceSummaryUseCase: GetUserBalanceSummaryUseCase,
    private val transactionsCategorizedByDateUseCase: GetUserTransactionsCategorizedByDateUseCase,
    private val addUserTransactionUseCase: AddUserTransactionUseCase,
    private val errorToUserMessage: ErrorToUserMessage,
) : ViewModel() {
    private val initialViewState: TransactionsHistoryViewState = LoadingState
    val viewState: MutableState<TransactionsHistoryViewState> =
        mutableStateOf(initialViewState)

    init {
        viewModelScope.launch {
            loadTransactionsHistory()
        }
    }

    private suspend fun loadTransactionsHistory() {
        runCatching {
            val transactionHistory = transactionsCategorizedByDateUseCase.invoke()
            transactionHistory.takeIf { it.isNotEmpty() }?.let {
                balanceSummaryUseCase.invoke()
            }?.run {
                Pair(this, transactionHistory)
            }
        }.onSuccess {
            viewState.value = TransactionsHistoryState(data = it)
        }.onFailure {
            viewState.value = ErrorState(error = errorToUserMessage.toUserMessage(it))
        }
    }

    fun onAddTransaction(
        currentStateData: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?,
        transaction: Transaction,
    ) {
        viewModelScope.launch {
            viewState.value = LoadingState
            runCatching {
                addUserTransactionUseCase.invoke(transaction)
            }.onSuccess {
                loadTransactionsHistory()
            }.onFailure {
                viewState.value = ErrorState(error = errorToUserMessage.toUserMessage(it),
                    data = currentStateData)
            }
        }
    }
}
