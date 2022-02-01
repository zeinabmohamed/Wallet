package com.zm.org.balance.ui.usertransactions

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.zm.org.balance.R
import com.zm.org.balance.data.model.TransactionType
import com.zm.org.balance.domain.entity.Transaction
import com.zm.org.balance.util.TimeMillis


@Composable
fun AddTransactionDialog(
    context: Context,
    openDialog: MutableState<Boolean>,
    onAddActionClicked: (Transaction) -> Unit,
) {
    var transactionTitleState = remember { mutableStateOf("") }
    var transactionTitleErrorState = remember { mutableStateOf(false) }
    var transactionAmountState = remember { mutableStateOf("") }
    var transactionAmountErrorState = remember { mutableStateOf(false) }
    var expanded = remember { mutableStateOf(false) }
    var selectedIndex = remember { mutableStateOf(-1) }
    val transactionTypes = TransactionType.values().map { it.toString() }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.add_transaction),
                    style = TextStyle(fontSize = 20.sp))
            }
        },
        text = {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Box(modifier = Modifier.wrapContentHeight().align(Alignment.Start)) {
                    Text(if (selectedIndex.value == -1)
                        stringResource(R.string.transaction_type)
                    else
                        transactionTypes[selectedIndex.value],
                        modifier = Modifier.fillMaxWidth().border(
                            border = BorderStroke(dimensionResource(R.dimen.padding_very_small),
                                Color.LightGray),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
                        ).padding(dimensionResource(R.dimen.padding_medium))
                            .clickable(onClick = { expanded.value = true }))
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        transactionTypes.forEachIndexed { index, itemLabel ->
                            DropdownMenuItem(onClick = {
                                selectedIndex.value = index
                                expanded.value = false
                            }) {
                                Text(itemLabel)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))

                OutlinedTextField(
                    value = transactionTitleState.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newValue ->
                        if(newValue.isNotBlank()){
                            transactionTitleErrorState.value = false
                        }
                        transactionTitleState.value = newValue
                    },
                    label = { Text(stringResource(R.string.transaction_desc)) },
                    singleLine = true,
                    isError = transactionTitleErrorState.value
                )

                Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))

                OutlinedTextField(
                    value = transactionAmountState.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newValue ->
                        if(newValue.isNotBlank()){
                            transactionAmountErrorState.value = false
                        }
                        transactionAmountState.value = newValue
                    },
                    label = { Text(stringResource(R.string.transaction_amount)) },
                    singleLine = true,
                    isError = transactionAmountErrorState.value,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )

            }
        },
        confirmButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (selectedIndex.value == -1) {
                            Toast.makeText(context,
                                context.getString(R.string.add_transaction),
                                Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (transactionTitleState.value.isNullOrEmpty()) {
                            transactionTitleErrorState.value = true
                            return@Button
                        }

                        if (transactionAmountState.value.isNullOrEmpty()) {
                            transactionAmountErrorState.value = true
                            return@Button
                        }

                        onAddActionClicked(
                            Transaction(
                                title = transactionTitleState.value,
                                amount = transactionAmountState.value.toFloat(),
                                type = TransactionType.valueOf(transactionTypes[selectedIndex.value]),
                                creationDateMillis = TimeMillis()
                            )
                        )
                        openDialog.value = false
                    }) {
                    Text(stringResource(R.string.add))
                }
            }
        },
    )
}
