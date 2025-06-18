package com.henrick.stock.model

import com.squareup.moshi.Json

/**
 * 上市個股日本益比、殖利率及股價淨值比（依代碼查詢）
 *
 * @param   Code    string  股票代號
 * @param   Name    string  股票名稱
 * @param   PEratio    string  本益比
 * @param   DividendYield    string  殖利率(%)
 * @param   PBratio    string  股價淨值比
 *
 */
data class StockRatio(
    @Json(name = "Code") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "PEratio") var peRatio: String?,
    @Json(name = "DividendYield") var dividendYield: String?,
    @Json(name = "PBratio") var pbRatio: String?,
)