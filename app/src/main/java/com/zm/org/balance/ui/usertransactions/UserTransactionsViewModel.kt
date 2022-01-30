package com.zm.org.balance.ui.usertransactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserTransactionsViewModel @Inject constructor(
    private val balanceSummaryUseCase: GetUserBalanceSummaryUseCase,
    private val transactionsCategorizedByDateUseCase: GetUserTransactionsCategorizedByDateUseCase,
) : ViewModel() {
    private val initialViewState: TransactionsHistoryViewState = LoadingState
    val viewStateLiveData: MutableState<TransactionsHistoryViewState> =
        mutableStateOf(initialViewState)

}
