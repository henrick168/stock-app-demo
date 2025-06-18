package com.henrick.stock.data

import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import kotlinx.coroutines.flow.Flow

interface StockDataSource {
    suspend fun getStockDayLists(): Flow<List<StockDay>>
    suspend fun getStockDayAvgLists(): Flow<List<StockDayAvg>>
    suspend fun getStockRatioLists(): Flow<List<StockRatio>>
}