package com.zm.org.balance.ui.usertransactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.LoadingState

class UserTransactionsViewModel : ViewModel() {
    private val initialViewState: TransactionsHistoryViewState = LoadingState
    val viewStateLiveData: MutableState<TransactionsHistoryViewState> = mutableStateOf(initialViewState)

}
