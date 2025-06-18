package com.henrick.stock.network

import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import retrofit2.http.GET

interface StockApiService {
    @GET("/v1/exchangeReport/STOCK_DAY_ALL")
    suspend fun getStockDayLists(): List<StockDay>

    @GET("/v1/exchangeReport/STOCK_DAY_AVG_ALL")
    suspend fun getStockDayAvgLists(): List<StockDayAvg>

    @GET("/v1/exchangeReport/BWIBBU_ALL")
    suspend fun getStockRatioLists(): List<StockRatio>
}