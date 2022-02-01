package com.zm.org.balance.ui.usertransactions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.zm.org.balance.R

@Composable
internal fun EmptyTrasnationsView() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth().padding(dimensionResource(R.dimen.padding_very_large)),
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.empty_wallet), contentDescription = "")
    }
}
