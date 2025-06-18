package com.henrick.stock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrick.stock.R

@Composable
fun StockListNotFoundScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_search_off_24),
            contentDescription = "No results found",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "No Result Found.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "We can't find any item matching your search.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.search_again))
        }
    }
}

@Preview
@Composable
private fun StockListNotFoundScreenPreview() {
    StockListNotFoundScreen(onClick = {})
}