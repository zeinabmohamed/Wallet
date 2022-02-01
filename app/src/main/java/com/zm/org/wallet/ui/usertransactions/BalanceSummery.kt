package com.zm.org.wallet.ui.usertransactions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.zm.org.wallet.R
import com.zm.org.wallet.domain.entity.UserBalanceSummary
import com.zm.org.wallet.util.MoneyFormatter

@Composable
internal fun BalanceSummery(moneyFormatter: MoneyFormatter, balanceSummary: UserBalanceSummary) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
            .background(color = MaterialTheme.colors.surface)
            .padding(dimensionResource(R.dimen.padding_large)),
        border = BorderStroke(dimensionResource(R.dimen.padding_very_small), Color.LightGray),
        elevation = dimensionResource(R.dimen.padding_medium)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_large)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)
                    .padding(end = dimensionResource(R.dimen.padding_small)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.expenses))
                    Text(stringResource(R.string.expense_amount,
                        moneyFormatter.format(balanceSummary.totalExpenses)), maxLines = 1,
                        modifier = Modifier.testTag("expensesAmountLabel"),
                        overflow = TextOverflow.Ellipsis)
                }
                Divider(
                    modifier = Modifier.fillMaxHeight()
                        .width(dimensionResource(R.dimen.padding_very_small)),
                    color = Color.LightGray
                )
                Column(Modifier.weight(1f)
                    .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.income))
                    Text(stringResource(R.string.income_amount,
                        moneyFormatter.format(balanceSummary.totalIncomes)), maxLines = 1,
                        modifier = Modifier.testTag("incomesAmountLabel"),
                        overflow = TextOverflow.Ellipsis)
                }
                Divider(
                    modifier = Modifier.fillMaxHeight()
                        .width(dimensionResource(R.dimen.padding_very_small)),
                    color = Color.LightGray
                )
                Column(Modifier.weight(1f)
                    .padding(start = dimensionResource(R.dimen.padding_small)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.balance))
                    Text(stringResource(R.string.income_amount,
                        moneyFormatter.format(balanceSummary.balance)),
                        modifier = Modifier.testTag("balanceAmountLabel"),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
                    .height(dimensionResource(R.dimen.padding_large)),
                progress = if (balanceSummary.balance <= 0) {
                    1f
                } else {
                    ((balanceSummary.totalExpenses) / balanceSummary.totalIncomes)
                },
            )
        }
    }
}

@Preview
@Composable
internal fun BalanceSummeryPreview() {
    Surface {
        BalanceSummery(MoneyFormatter(), UserBalanceSummary(
            20f,
            -1000f,
            50f,
        ))
    }
}
