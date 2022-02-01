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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.zm.org.balance.R
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.util.MoneyFormatter
import com.zm.org.balance.util.TimeMillis
import java.util.*

@Composable
internal fun TransactionsHistoryList(
    moneyFormatter: MoneyFormatter,
    transactionHistory: Map<TimeMillis, List<Transaction>>,
) {
    LazyColumn(state = rememberLazyListState()) {
        items(transactionHistory.toList()) { transactionsForDay ->
            TransactionsHistoryRow(moneyFormatter,
                transactionsForDay.first,
                transactionsForDay.second)
        }
    }
}

@Composable
internal fun TransactionsHistoryRow(
    moneyFormatter: MoneyFormatter,
    dayTimeMillis: TimeMillis,
    transactionList: List<Transaction>,
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
            .background(color = MaterialTheme.colors.surface)
            .padding(dimensionResource(R.dimen.padding_large)),
        border = BorderStroke(dimensionResource(R.dimen.padding_very_small), Color.LightGray),
        elevation = dimensionResource(R.dimen.padding_medium)
    ) {
        Column {
            Text(dayTimeMillis.getDayStr(),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
            Divider(
                modifier = Modifier.fillMaxWidth()
                    .height(dimensionResource(R.dimen.padding_very_small)),
                color = Color.LightGray
            )
            Column {
                transactionList.forEachIndexed { index, transaction ->
                    TransactionRow(moneyFormatter, transaction)
                    if (index != (transactionList.size - 1)) {
                        Divider(
                            modifier = Modifier.fillMaxWidth()
                                .height(dimensionResource(R.dimen.padding_very_small)),
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}


@Composable
internal fun TransactionRow(moneyFormatter: MoneyFormatter, transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium)).testTag("transactionHistoryListRow"),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(transaction.title,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Text(stringResource((if (transaction.type == TransactionType.EXPENSE)
            R.string.expense_amount else R.string.income_amount),
            moneyFormatter.format(transaction.amount)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)

    }
}

@Preview
@Composable
internal fun TransactionsHistoryListPreview() {
    Surface {
        TransactionsHistoryList(MoneyFormatter(), mapOf(
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
