package com.zm.org.balance.ui.usertransactions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.zm.org.balance.domain.entity.UserBalanceSummary

@Composable
internal fun BalanceSummery(balanceSummary: UserBalanceSummary) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
            .background(color = MaterialTheme.colors.surface)
            .padding(dimensionResource(R.dimen.padding_large)),
        border = BorderStroke(dimensionResource(R.dimen.padding_very_small), Color.Gray),
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
                Column {
                    Text(stringResource(R.string.expenses))
                    Text(stringResource(R.string.expense_amount, balanceSummary.totalExpenses))
                }
                Divider(
                    modifier = Modifier.fillMaxHeight()
                        .width(dimensionResource(R.dimen.padding_very_small)),
                    color = Color.Gray
                )
                Column {
                    Text(stringResource(R.string.income))
                    Text(stringResource(R.string.income_amount, balanceSummary.totalIncomes))
                }
                Divider(
                    modifier = Modifier.fillMaxHeight()
                        .width(dimensionResource(R.dimen.padding_very_small)),
                    color = Color.Gray
                )
                Column {
                    Text(stringResource(R.string.balance))
                    Text(stringResource(R.string.income_amount, balanceSummary.balance))
                }
            }
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
                    .height(dimensionResource(R.dimen.padding_large)),
                progress = ((balanceSummary.totalExpenses) / balanceSummary.totalIncomes),
            )
        }
    }
}

@Preview
@Composable
internal fun BalanceSummeryPreview() {
    Surface {
        BalanceSummery(UserBalanceSummary(
            20f,
            70f,
            50f,
        ))
    }
}
