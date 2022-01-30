package com.zm.org.balance.ui.usertransactions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zm.org.balance.R
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.util.TimeMillis
import java.util.*

@Composable
internal fun TransactionsHistoryList(transactionHistory: Map<TimeMillis, List<Transaction>>) {
    LazyColumn(state = rememberLazyListState()) {
        items(transactionHistory.toList()) { transactionsForDay ->
            TransactionsHistoryRow(transactionsForDay.first, transactionsForDay.second)
        }
    }
}

@Composable
internal fun TransactionsHistoryRow(
    dayTimeMillis: TimeMillis,
    transactionList: List<Transaction>,
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
            .background(color = MaterialTheme.colors.surface)
            .padding(dimensionResource(R.dimen.padding_large)),
        border = BorderStroke(dimensionResource(R.dimen.padding_very_small), Color.Gray),
        elevation = dimensionResource(R.dimen.padding_medium)
    ) {
        Column {
            Text(dayTimeMillis.getDayStr(), modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
            Divider(
                modifier = Modifier.fillMaxWidth()
                    .height(dimensionResource(R.dimen.padding_very_small)),
                color = Color.Gray
            )
            Column {
                transactionList.forEachIndexed { index, transaction ->
                    TransactionRow(transaction)
                    if (index != (transactionList.size - 1)) {
                        Divider(
                            modifier = Modifier.fillMaxWidth()
                                .height(dimensionResource(R.dimen.padding_very_small)),
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Composable
internal fun TransactionRow(transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(transaction.title)
        Text(stringResource((if (transaction.type == TransactionType.EXPENSE)
            R.string.expense_amount else R.string.income_amount), transaction.amount))

    }
}

@Preview
@Composable
internal fun TransactionsHistoryListPreview() {
    Surface {
        TransactionsHistoryList(mapOf(
            Pair(
                TimeMillis(), listOf(
                    Transaction(
                        title = "EXPENSE #1",
                        amount = 4f,
                        type = TransactionType.EXPENSE,
                        creationDateMillis = TimeMillis()
                    ),
                    Transaction(
                        title = "INCOME #1",
                        amount = 10.22f,
                        type = TransactionType.INCOME,
                        creationDateMillis = TimeMillis()
                    )
                )
            ),
            Pair(
                TimeMillis(Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, 22)
                }.timeInMillis), listOf(
                    Transaction(
                        title = "EXPENSE #1",
                        amount = 12.2222f,
                        type = TransactionType.EXPENSE,
                        creationDateMillis = TimeMillis()
                    ),
                    Transaction(
                        title = "INCOME #1",
                        amount = 60.1f,
                        type = TransactionType.INCOME,
                        creationDateMillis = TimeMillis()
                    )
                )
            )
        ))
    }
}
