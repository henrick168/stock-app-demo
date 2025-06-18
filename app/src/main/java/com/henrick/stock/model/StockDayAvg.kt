package com.henrick.stock.model

import com.squareup.moshi.Json

/**
 * 上市個股日收盤價及月平均價
 *
 * @param Code    string    股票代號
 * @param   Name    string  股票名稱
 * @param   ClosingPrice    string  收盤價
 * @param   MonthlyAveragePrice    string  月平均價
 *
 */
data class StockDayAvg(
    @Json(name = "Code") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "ClosingPrice") val closingPrice: String,
    @Json(name = "MonthlyAveragePrice") val monthlyAveragePrice: String,
)