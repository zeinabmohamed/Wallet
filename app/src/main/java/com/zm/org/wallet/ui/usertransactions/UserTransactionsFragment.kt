package com.zm.org.wallet.ui.usertransactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zm.org.wallet.R
import com.zm.org.wallet.domain.entity.Transaction
import com.zm.org.wallet.domain.entity.UserBalanceSummary
import com.zm.org.wallet.ui.usertransactions.TransactionsHistoryViewState.*
import com.zm.org.wallet.util.MoneyFormatter
import com.zm.org.wallet.util.TimeMillis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserTransactionsFragment : Fragment() {

    private val viewModel: UserTransactionsViewModel by viewModels()

    @Inject
    lateinit var moneyFormatter: MoneyFormatter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val state = viewModel.viewState.value
            UserTransactionsView(moneyFormatter, state)
        }
    }

    @Composable
    fun UserTransactionsView(
        moneyFormatter: MoneyFormatter,
        transactionsHistoryListViewState: TransactionsHistoryViewState,
    ) {
        val openDialog = remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        stringResource(R.string.user_transaction_history),
                        modifier = Modifier.testTag("screenTitle"),
                    )
                })
            },
            floatingActionButton = {
                if (transactionsHistoryListViewState !is LoadingState) {
                    FloatingActionButton(
                       modifier =  Modifier.testTag("addTransactionButton"),
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
                    SuccessTransactionHistoryView(moneyFormatter, openDialog,
                        transactionsHistoryListViewState.data)
                is ErrorState -> {
                    SuccessTransactionHistoryView(moneyFormatter, openDialog,
                        transactionsHistoryListViewState.data)
                    Toast.makeText(requireContext(),
                        transactionsHistoryListViewState.error,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Composable
    private fun SuccessTransactionHistoryView(
        moneyFormatter: MoneyFormatter,
        openDialog: MutableState<Boolean>,
        data: Pair<UserBalanceSummary, Map<TimeMillis, List<Transaction>>>?,
    ) {
        data?.let {
            Column {
                data.first.let { balanceHistory ->
                    BalanceSummery(moneyFormatter, balanceHistory)
                }
                data.second.let { transactionsHistory ->
                    TransactionsHistoryList(moneyFormatter, transactionsHistory)
                }
            }
        } ?: let {
            EmptyTrasnationsView()
        }
        if (openDialog.value) {
            AddTransactionDialog(
                requireContext(),
                openDialog
            ) {
                viewModel.onAddTransaction(data, it)
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
