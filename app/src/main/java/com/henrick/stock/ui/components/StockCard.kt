package com.henrick.stock.ui.components

import android.content.res.Configuration
import androidx.annotation.ColorRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrick.stock.R
import com.henrick.stock.model.StockInfo
import com.henrick.stock.ui.theme.StockAppTheme

@Composable
fun StockCard(
    stockInfo: StockInfo,
    modifier: Modifier = Modifier,
) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier.clickable { showDialog = true }
    ) {
        // 股票代號
        Text(
            text = stockInfo.code,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        // 股票名稱
        Text(
            text = stockInfo.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 8.dp)
        )

        val isDarkTheme = isSystemInDarkTheme()

        // 開盤價、收盤價
        TwoLabelItem(
            labelOne = stringResource(R.string.label_text_stock_opening_price),
            labelOneValue = stockInfo.getOpeningPriceString(),
            labelTwo = stringResource(R.string.label_text_stock_closing_price),
            labelTwoValue = stockInfo.getClosingPriceString(),
            labelTwoColor = stockInfo.getClosingPriceStringColor(isDarkTheme),
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, end = 16.dp)
        )

        // 最高價、最低價
        TwoLabelItem(
            labelOne = stringResource(R.string.label_text_stock_highest_price),
            labelOneValue = stockInfo.getHighestPriceString(),
            labelTwo = stringResource(R.string.label_text_stock_lowest_price),
            labelTwoValue = stockInfo.getLowestPriceString(),
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, end = 16.dp)
        )

        // 漲跌價差、月平均價
        TwoLabelItem(
            labelOne = stringResource(R.string.label_text_stock_price_difference),
            labelOneValue = stockInfo.getChangePriceString(),
            labelOneColor = stockInfo.getStockDiffStringColor(isDarkTheme),
            labelTwo = stringResource(R.string.label_text_stock_monthly_average_price),
            labelTwoValue = stockInfo.getMonthlyAvgPriceString(),
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, end = 16.dp)
        )

        Row(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.label_text_stock_number_of_transactions),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1.1f)
            )
            Text(
                text = stockInfo.getTransactionString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1.1f)
            )
            Spacer(Modifier.width(1.dp))
            Text(
                text = stringResource(R.string.label_text_stock_number_of_shares_traded),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1.1f)
            )
            Text(
                text = stockInfo.getTradeVolumeString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1.3f)
            )
            Spacer(Modifier.width(1.dp))
            Text(
                text = stringResource(R.string.label_text_stock_trading_amount),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1.1f)
            )
            Text(
                text = stockInfo.getTradeValueString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .weight(1.5f)
                //.padding(end = 8.dp)
            )
        }
    }

    if (showDialog) {
        StockInfoDialog(
            stockInfo = stockInfo,
            onDismissRequest = { showDialog = false },
        )
    }
}

@Composable
fun TwoLabelItem(
    labelOne: String = "",
    labelOneValue: String = "",
    @ColorRes labelOneColor: Color = Color.Unspecified,
    labelTwo: String = "",
    labelTwoValue: String = "",
    @ColorRes labelTwoColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = labelOne,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = labelOneValue,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            color = if (labelOneColor != Color.Unspecified) labelOneColor else Color.Unspecified,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = labelTwo,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = labelTwoValue,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            color = if (labelTwoColor != Color.Unspecified) labelTwoColor else Color.Unspecified,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(
    showBackground = true, name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true)
@Composable
private fun StockCardPreview() {
    StockAppTheme {
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
        StockCard(stockInfo)
    }
}

@Composable
fun StockPlaceHolderCard(modifier: Modifier = Modifier) {

    // 定義動畫的顏色狀態
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.2f), // 初始亮灰色
        targetValue = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 1f),   // 目標白色 (或更亮的灰色)
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700, // 動畫持續時間
                easing = FastOutLinearInEasing // 動畫的 easing 效果
            ),
            repeatMode = RepeatMode.Reverse // 反向重複動畫
        )
    )

    Card(modifier = modifier) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .background(animatedColor)
                .height(160.dp)

        )
    }
}

@Preview
@Composable
private fun StockPlaceHolderCardPreview() {
    StockPlaceHolderCard()
}