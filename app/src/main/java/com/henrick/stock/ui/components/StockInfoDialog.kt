package com.henrick.stock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrick.stock.R
import com.henrick.stock.model.StockInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockInfoDialog(
    stockInfo: StockInfo,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
            )
            .padding(10.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stockInfo.getStockInfoDialogTitle(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Text(
                text = stringResource(
                    R.string.label_text_stock_pe_ratio,
                    stockInfo.getPeRatioString()
                ),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(start = 0.dp, bottom = 4.dp)
            )
            Text(
                text = stringResource(
                    R.string.label_text_stock_dividend_yield,
                    stockInfo.getDividendYieldString()
                ),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(start = 0.dp, bottom = 4.dp)
            )
            Text(
                text = stringResource(
                    R.string.label_text_stock_pb_ratio,
                    stockInfo.getPbRatioString()
                ),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(start = 0.dp, bottom = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StockInfoDialogPreview() {
    val stockInfo = StockInfo(
        code = "2330",
        name = "台積電",
        tradeVolume = "54443912",
        tradeValue = "56293841760",
        openingPrice = "1045.00",
        highestPrice = "1050.00",
        lowestPrice = "1030.00",
        closingPrice = "1030.00",
        change = "-10.0000",
        transaction = "34710",
        monthlyAveragePrice = "1043.53",
        peRatio = "21.20",
        dividendYield = "1.77",
        pbRatio = "5.80"
    )
    StockInfoDialog(
        stockInfo = stockInfo,
        onDismissRequest = {},
    )
}