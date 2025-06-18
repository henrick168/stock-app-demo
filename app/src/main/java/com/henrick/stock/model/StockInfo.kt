package com.henrick.stock.model

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.compose.ui.graphics.Color


/**
 * 上市個股日成交資訊，合併月平均價、本益比、殖利率及股價淨值比。
 *
 * @param Code    string    證券代號
 * @param Name    string 證券名稱
 * @param TradeVolume    string 成交股數
 * @param TradeValue    string  成交金額
 * @param OpeningPrice    string  開盤價
 * @param HighestPrice    string  最高價
 * @param LowestPrice    string  最低價
 * @param ClosingPrice    string  收盤價
 * @param Change    string  漲跌價差
 * @param Transaction    string  成交筆數
 * @param MonthlyAveragePrice    string  月平均價
 * @param PEratio    string  本益比
 * @param DividendYield    string  殖利率(%)
 * @param PBratio    string  股價淨值比
 *
 */
const val STOCK_DEFAULT_STRING = "N/A"
data class StockInfo(
    val code: String,
    val name: String,
    var tradeVolume: String? = null,
    var tradeValue: String? = null,
    var openingPrice: String? = null,
    var highestPrice: String? = null,
    var lowestPrice: String? = null,
    var closingPrice: String? = null,
    var change: String? = null,
    var transaction: String? = null,
    var monthlyAveragePrice: String? = null,
    var peRatio: String? = null,
    var dividendYield: String? = null,
    var pbRatio: String? = null,
) {

    // 用於淺色背景 (surfaceContainerLight) 上的顏色 (需要深色)
    private val onSurfaceContainerRedLight = Color(0xFFD50000)   // 深紅色 (表示上漲 ▲) - Dark Red for Gains
    private val onSurfaceContainerGreenLight = Color(0xFF00C853)  // 深綠色 (表示下跌 ▼) - Dark Green for Losses
    // 用於深色背景 (surfaceContainerDark) 上的顏色 (需要淺色)
    private val onSurfaceContainerRedDark = Color(0xFFFF1744)   // 淺紅色 (表示上漲 ▲) - Light Red for Gains
    private val onSurfaceContainerGreenDark = Color(0xFF00E676)  // 淺綠色 (表示下跌 ▼) - Light Green for Losses

    @VisibleForTesting
    fun getColorRedByDarkTheme(isDarkTheme: Boolean): Color {
        return if (isDarkTheme) onSurfaceContainerRedDark else onSurfaceContainerRedLight
    }

    @VisibleForTesting
    fun getColorGreenByDarkTheme(isDarkTheme: Boolean): Color {
        return if (isDarkTheme) onSurfaceContainerGreenDark else onSurfaceContainerGreenLight
    }

    /**
     * 漲跌價差文字顏色
     *
     * 正的請用紅字,負的請用綠字
     */
    fun getStockDiffStringColor(isDarkTheme: Boolean): Color {
        return if (getPriceString(change) == STOCK_DEFAULT_STRING || getPriceString(change).toDouble() == 0.0) {
            Color.Unspecified
        } else if (change!!.toDouble() > 0.0) {
            getColorRedByDarkTheme(isDarkTheme)
        } else {
            getColorGreenByDarkTheme(isDarkTheme)
        }
    }

    /**
     * 收盤價文字顏色
     *
     * 收盤價高於月平均價請用紅字,低於請用綠字顯示
     */
    fun getClosingPriceStringColor(isDarkTheme: Boolean): Color {
        return if (getPriceString(closingPrice) == STOCK_DEFAULT_STRING || getPriceString(monthlyAveragePrice) == STOCK_DEFAULT_STRING) {
            Color.Unspecified
        } else if (closingPrice!!.toDouble() > monthlyAveragePrice!!.toDouble()) {
            getColorRedByDarkTheme(isDarkTheme)
        } else {
            getColorGreenByDarkTheme(isDarkTheme)
        }
    }

    private fun getPriceString(value: String?): String {
        return value?.trim()?.toDoubleOrNull()?.toString() ?: STOCK_DEFAULT_STRING
    }

    private fun getTradeString(value: String?): String {
        return if (value?.trim().isNullOrBlank()) STOCK_DEFAULT_STRING else value!!.trim()
    }

    @SuppressLint("DefaultLocale")
    private fun formatToMillions(valueString: String): String {
        return try {
            if (valueString == STOCK_DEFAULT_STRING) {
                valueString
            } else {
                val valueMillions = valueString.toDouble() / 1000000.0
                "${String.format("%.2f", valueMillions)}M"
            }
        } catch (e: Exception) {
            "Invalid Number."
        }
    }

    fun getOpeningPriceString() = getPriceString(openingPrice)

    fun getClosingPriceString() = getPriceString(closingPrice)

    fun getHighestPriceString() = getPriceString(highestPrice)

    fun getLowestPriceString() = getPriceString(lowestPrice)

    fun getChangePriceString() = getPriceString(change)

    fun getMonthlyAvgPriceString() = getPriceString(monthlyAveragePrice)

    fun getTransactionString() = getTradeString(transaction)

    fun getTradeVolumeString() = getTradeString(tradeVolume)

    fun getTradeValueString() = formatToMillions(getTradeString(tradeValue))

    fun getPeRatioString() = getPriceString(peRatio)

    fun getPbRatioString() = getPriceString(pbRatio)

    fun getDividendYieldString() = getPriceString(dividendYield).let { text ->
        if (text != STOCK_DEFAULT_STRING) "$text%" else text
    }

    fun getStockInfoDialogTitle() = "(${code})${name}"
}
