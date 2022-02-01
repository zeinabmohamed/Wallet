package com.zm.org.balance.ui.usertransactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zm.org.balance.R
import com.zm.org.balance.ui.usertransactions.TransactionsHistoryViewState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserTransactionsFragment : Fragment() {

    private val viewModel: UserTransactionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val state = viewModel.viewState.value
            UserTransactionsView(state)
        }
    }

    @Composable
    fun UserTransactionsView(
        transactionsHistoryListViewState: TransactionsHistoryViewState,
    ) {
        val openDialog = remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.user_transaction_history))
                })
            },
            floatingActionButton = {
                if (transactionsHistoryListViewState !is LoadingState) {
                    FloatingActionButton(
                        onClick = {
                            openDialog.value = true
                        }) {
                        Icon(Icons.Filled.Add, "")
                    }
                }
            }
        ) {
            when (transactionsHistoryListViewState) {
                is LoadingState -> LoadingStateView()
                is TransactionsHistoryState ->
                    Column {
                        transactionsHistoryListViewState.data?.first?.let { balanceHistory ->
                            BalanceSummery(balanceHistory)
                        }
                        transactionsHistoryListViewState.data?.second?.let { transactionsHistory ->
                            TransactionsHistoryList(transactionsHistory)
                        }
                        if (openDialog.value) {
                            AddTransactionDialog(
                                requireContext(),
                                openDialog
                            ) {
                                viewModel.onAddTransaction(it)
                            }
                        }
                    }
            }
        }
    }


    @Preview
    @Composable
    private fun LoadingStateView() {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.width(100.dp).height(100.dp)) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
            }
            Spacer(Modifier.height(dimensionResource(R.dimen.padding_large)))
            Text(stringResource(R.string.loading))
        }
    }

}
