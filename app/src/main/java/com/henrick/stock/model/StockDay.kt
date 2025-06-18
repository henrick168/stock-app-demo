package com.henrick.stock.model

import com.squareup.moshi.Json

/**
 * 上市個股日成交資訊
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
 *
 */
data class StockDay(
    @Json(name = "Code") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "TradeVolume") var tradeVolume: String?,
    @Json(name = "TradeValue") var tradeValue: String?,
    @Json(name = "OpeningPrice") var openingPrice: String?,
    @Json(name = "HighestPrice") var highestPrice: String?,
    @Json(name = "LowestPrice") var lowestPrice: String?,
    @Json(name = "ClosingPrice") var closingPrice: String?,
    @Json(name = "Change") var change: String?,
    @Json(name = "Transaction") var transaction: String?,
)