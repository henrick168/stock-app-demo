package com.henrick.stock.repository

import com.henrick.stock.data.remote.RemoteStockDataSource
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class StockRepository @Inject constructor(
    private val remoteStockDataSource: RemoteStockDataSource,
) {
    open suspend fun getStockDayLists(): Flow<List<StockDay>> = remoteStockDataSource.getStockDayLists()

    open suspend fun getStockDayAvgLists(): Flow<List<StockDayAvg>> = remoteStockDataSource.getStockDayAvgLists()

    open suspend fun getStockRatioLists(): Flow<List<StockRatio>> = remoteStockDataSource.getStockRatioLists()
}